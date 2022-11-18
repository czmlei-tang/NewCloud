import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.tang.newcloud.service.trade.util.ConnectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
public class Task01 {
    private static final String QUEUE_NAME="newcloud_test_order";
    private static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
//        try (Connection connection = ConnectionUtil.getConnection();
//             Channel channel = connection.createChannel()) {
////从控制台当中接受信息
//            channel.confirmSelect();
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext()){
//                String message = scanner.next();
//                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
//                boolean flag = channel.waitForConfirms();
//                if(flag){
//                    log.info("发送消息完成:"+message);
//                }
//                else log.info("发送消息errror:"+message);
//            }
//        }
        publishMessageAsync();
    }

    public static void publishMessageAsync() throws Exception {
        try (Connection connection = ConnectionUtil.getConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //开启发布确认
            channel.confirmSelect();
            /**
             * 线程安全有序的一个哈希表，适用于高并发的情况
             * 1.轻松的将序号与消息进行关联
             * 2.轻松批量删除条目 只要给到序列号
             * 3.支持并发访问
             */
            ConcurrentSkipListMap<Long, String> outstandingConfirms = new
                    ConcurrentSkipListMap<>();
            /**
             * 确认收到消息的一个回调
             * 1.消息序列号
             * 2.true 可以确认小于等于当前序列号的消息
             * false 确认当前序列号消息
             */
            ConfirmCallback ackCallback = (sequenceNumber, multiple) -> {
                if (multiple) {
                //返回的是小于等于当前序列号的未确认消息 是一个 map
                    ConcurrentNavigableMap<Long, String> confirmed =
                            outstandingConfirms.headMap(sequenceNumber, true);
                    //清除该部分未确认消息
                    confirmed.clear();
                }else{
                    //只清除当前序列号的消息
                    outstandingConfirms.remove(sequenceNumber);
                }
            };
            ConfirmCallback nackCallback = (sequenceNumber, multiple) ->
            {
                String message = outstandingConfirms.get(sequenceNumber);
                System.out.println("发布的消息"+message+"未被确认，序列号"+sequenceNumber);
            };
            /**
             * 添加一个异步确认的监听器
             * 1.确认收到消息的回调
             * 2.未收到消息的回调
             */
            channel.addConfirmListener(ackCallback, null);
            long begin = System.currentTimeMillis();

            for (int i = 0; i < MESSAGE_COUNT; i++)
            {
                String message = "消息" + i;
            /**
             * channel.getNextPublishSeqNo()获取下一个消息的序列号
             * 通过序列号与消息体进行一个关联
             * 全部都是未确认的消息体
             */
                outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            long end = System.currentTimeMillis();
            System.out.println("发布" + MESSAGE_COUNT + "个异步确认消息,耗时" + (end - begin) +"ms");
            }
        }
}
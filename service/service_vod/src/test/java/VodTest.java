import com.tang.newcloud.service.vod.ServiceVodApplication;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ServiceVodApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VodTest {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test1(){
        System.out.println("...........");
    }
}

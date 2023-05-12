package com.tang.newcloud.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.edu.entity.Comment;
import com.tang.newcloud.service.edu.entity.vo.web.*;
import com.tang.newcloud.service.edu.mapper.SubjectMapper;
import com.tang.newcloud.service.edu.service.CommentService;
import com.tang.newcloud.service.edu.mapper.CommentMapper;
import com.tang.newcloud.service.edu.service.GoodNumberService;
import com.tang.newcloud.service.edu.util.RedisKeyUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
* @author 29878
* @description 针对表【edu_comment(评论)】的数据库操作Service实现
* @createDate 2023-01-06 14:07:48
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    private GoodNumberService goodNumberService;

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Map getCommentList(Long page, Long limit, WebCommentQueryVo webCommentQueryVo) {
        Integer watchNumber = webCommentQueryVo.getWatchNumber();
        Integer gmtCreate = webCommentQueryVo.getGmtCreate();
        Integer answerNumber = webCommentQueryVo.getAnswerNumber();
        Integer subjectId = webCommentQueryVo.getSubjectId();
        HashMap<String, Object> hashMap = new HashMap<>();

        Page<Comment> commentPage = new Page<>(page, limit);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getStatus,1)
                .orderByDesc(watchNumber!=null&&watchNumber==1,Comment::getWatchNumber)
                .eq(answerNumber!=null&&answerNumber==1,Comment::getAnswerNumber,0)
                .orderByDesc(gmtCreate!=null&&gmtCreate==1,Comment::getGmtCreate)
                .isNotNull(subjectId!=null&&subjectId==1,Comment::getSubjectId);

        Page<Comment> page1 = baseMapper.selectPage(commentPage, wrapper);
        List<Comment> records = page1.getRecords();
        long total = page1.getTotal();
        if(total%limit==0){
            total=total/limit;
        }else total=total/limit+1;
        List<WebCommentIndexVo> collect = records.stream().map(comment -> {
            WebCommentIndexVo indexVo = new WebCommentIndexVo();
            BeanUtils.copyProperties(comment, indexVo);
            String bastAsk = commentMapper.selectOneCommentContentByAnswerId(comment.getId());
            String tag = subjectMapper.selectSubjectNameById(comment.getSubjectId());
            if(bastAsk==null||bastAsk==""){
                bastAsk="暂无";
            }else if(bastAsk.length()>20){
                bastAsk=bastAsk.substring(0,20);
            }
            indexVo.setBestAsk(bastAsk)
                    .setTag(tag);
            return indexVo;
        }   ).collect(Collectors.toList());

        List<WebCommentHotVo> list = gethot();
        List<WebCommentTagsVo> taglist = getTags();
        hashMap.put("total",total);
        hashMap.put("commentList",collect);
        hashMap.put("hotComments",list);
        hashMap.put("tags",taglist);
        return hashMap;
    }

    @Override
    public List<WebCommentTagsVo> getTags() {
        return subjectMapper.selectSubjects();
    }

    @Override
    public WebCommentBestAskVo getBestAsk(Long id, HttpServletRequest request) {
        WebCommentBestAskVo bestAskVo = new WebCommentBestAskVo();

        //如果用户登录，会判断一下点赞状态
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        Comment comment1 = commentMapper.selectBestComment(id);
        if(comment1!=null){
            Comment comment = commentMapper.selectIdAndNickName(id);
            bestAskVo.setAnswerId(comment.getId()).setAnswerNickName(comment.getNickname());
            BeanUtils.copyProperties(comment1,bestAskVo);

            //拼入总数
            RBucket<Long> totalBucket = redissonClient.getBucket(bestAskVo.getId());
            Long total = totalBucket.get();
            if(total!=null&&total!=0){
                bestAskVo.setGoodNumber(total);
            }else{
                bestAskVo.setGoodNumber(0L);
            }

            if(token!=null){

                String userId = token.getId();
                String goodNumberKey = RedisKeyUtils.getGoodNumberKey(String.valueOf(id), userId);
                RMap<String, Object> map = redissonClient.getMap(goodNumberKey);
                Integer status = (Integer)map.get("status");
                if(status==1){
                    bestAskVo.setGoodStatus(1);
                }else{
                    bestAskVo.setGoodStatus(0);
                }
            }else{
                bestAskVo.setGoodStatus(0);
            }
        }else{

        }
        return bestAskVo;
    }


    @Override
    public List<WebCommentVo> getSecondDataComments(Long parentId,HttpServletRequest request) {
        //如果用户登录，会判断一下点赞状态
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        List<WebCommentVo> webCommentVos = commentMapper.selectSecondComments(parentId);
        if(token!=null){
            String userId = token.getId();
            List<WebCommentVo> commentVos = webCommentVos.stream().map(webCommentVo -> {
                String goodNumberKey = RedisKeyUtils.getGoodNumberKey(webCommentVo.getId(), userId);

                //拼入总数
                RBucket<Long> totalBucket = redissonClient.getBucket(webCommentVo.getId());
                Long total = totalBucket.get();
                if(total!=null&&total!=0){
                    webCommentVo.setGoodNumber(total);
                }else{
                    webCommentVo.setGoodNumber(0L);
                }

                RMap<String, Object> map = redissonClient.getMap(goodNumberKey);
                Integer status = (Integer) map.get("status");
                if (status==null||status == 0) {
                    webCommentVo.setGoodStatus(0);
                } else {
                    webCommentVo.setGoodStatus(1);
                }
                return webCommentVo;
            }).collect(Collectors.toList());
            return commentVos;
        }else{
            webCommentVos.stream().forEach(i->{
                i.setGoodStatus(0);
            });
            return webCommentVos;
        }

    }

    @Override
    public WebCommentIndexVo getOneComment(Long id,HttpServletRequest request) {
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        Comment comment = commentMapper.selectOneComment(id);
        //watchnumber+1
        commentMapper.updateViewById(id);

        WebCommentIndexVo indexVo = new WebCommentIndexVo();
        BeanUtils.copyProperties(comment, indexVo);
        String tag = subjectMapper.selectSubjectNameById(comment.getSubjectId());
        indexVo.setTag(tag);
        //拼入总数
        RBucket<Long> totalBucket = redissonClient.getBucket(String.valueOf(id));
        Long total = totalBucket.get();
        if(total!=null&&total!=0){
            indexVo.setGoodNumber(total);
        }else{
            indexVo.setGoodNumber(0L);
        }
        if(token!=null){
            String userId = token.getId();
            String key = RedisKeyUtils.getGoodNumberKey(String.valueOf(id), userId);
            RMap<String, Object> map = redissonClient.getMap(key);
            Integer status = (Integer) map.get("status");
            if (status==null||status == 0) {
                indexVo.setGoodStatus(0);
            } else {
                indexVo.setGoodStatus(1);
            }
        }else{
            indexVo.setGoodStatus(0);
        }
        return indexVo;
    }

    /**
     * 2 流 递归
     * @param parentId
     * @return
     */
    @Override
    public List<WebCommentVo> getComments(Long parentId,HttpServletRequest request) {
        //一级数据
        //如果用户登录，会判断一下点赞状态
        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        List<WebCommentVo> webCommentVos = commentMapper.selectComments(parentId);
        if(token!=null) {
            String userId = token.getId();
            List<WebCommentVo> commentVos = webCommentVos.stream().map(webCommentVo -> {
                String goodNumberKey = RedisKeyUtils.getGoodNumberKey(webCommentVo.getId(), userId);
                //拼入总数
                RBucket<Long> totalBucket = redissonClient.getBucket(webCommentVo.getId());
                Long total = totalBucket.get();
                if(total!=null&&total!=0){
                    webCommentVo.setGoodNumber(total);
                }else{
                    webCommentVo.setGoodNumber(0L);
                }
                RMap<String, Object> map = redissonClient.getMap(goodNumberKey);
                Integer status = (Integer) map.get("status");
                if (status==null||status == 0) {
                    webCommentVo.setGoodStatus(0);
                } else {
                    webCommentVo.setGoodStatus(1);
                }
                return webCommentVo;
            }).collect(Collectors.toList());
            return commentVos;
        }else{
            webCommentVos.forEach(webCommentVo -> webCommentVo.setGoodStatus(0));
            return webCommentVos;
        }

    }

    @Override
    @Transactional
    public Boolean saveComment(Comment comment, JwtInfo jwtInfo) {
        String id = jwtInfo.getId();
        String avatar = jwtInfo.getAvatar();
        String nickname = jwtInfo.getNickname();
        comment.setMemberId(id).setAvatar(avatar).setNickname(nickname);
        int i = commentMapper.insert(comment);
        if(comment.getStatus()==0){
            String answerId = comment.getAnswerId();
            commentMapper.increaseAnswerNumber(answerId);
        }
        return i > 0;
    }

    @Override
    public Boolean removeCommentById(Long id,String memberId) {
        Comment comment = commentMapper.selectOneComment(id);
        boolean b = comment.getMemberId().equals(memberId);
        if(b){
            Integer i = commentMapper.deleteCommentById(id);
            return i > 0;
        }
        return false;
    }

    /**
     *
     * @param id 评论id
     * @param loginMemberId 当前用户id
     * @return
     */
    @Override
    public Boolean updateGoodNumber(Long id, String loginMemberId) {
        //查看redis里有没有key
        //key的形式 toid::fromid
        //redisson第一次自行创建
        String goodNumberKey = RedisKeyUtils.getGoodNumberKey(id.toString(), loginMemberId);
        RMap<Object, Object> map = redissonClient.getMap(goodNumberKey);
        Integer status = (Integer) map.get("status");
        //有就取消点赞
        //没有就点赞
        Boolean aBoolean =false;
        if(status!=null&&status!=0){
            aBoolean = goodNumberService.unlikeFromRedis(goodNumberKey);
        }else{
            aBoolean = goodNumberService.saveLiked2Redis(goodNumberKey);
        }
        return aBoolean;
    }

    @Override
    public List<WebCommentHotVo> getHotComment() {
        return gethot();
    }

    private List<WebCommentHotVo> gethot(){
        return commentMapper.selectHotComments();
    }
}





package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentHotVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebMyAnswerVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebMyQuestionVo;

import java.util.List;

/**
* @author 29878
* @description 针对表【edu_comment(评论)】的数据库操作Mapper
* @createDate 2023-01-06 14:07:48
* @Entity com.tang.newcloud.service.edu.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    Comment selectOneComment(Long id);

    List<WebCommentVo> selectComments(Long parentId);

    int updateViewById(Long id);

    Integer deleteCommentById(Long id);

    void updateGoodNumber(Long i,String id);

    String selectOneCommentContentByAnswerId(String id);

    List<WebCommentHotVo> selectHotComments();

    Comment selectIdAndNickName(Long id);

    Comment selectBestComment(Long id);

    List<WebCommentVo> selectSecondComments(Long id);

    void increaseAnswerNumber(String answerId);

    int updateComment(String memberId, String nickname, String avatar);

    List<WebMyQuestionVo> selectWebMyQuestionVo(String id);

    Integer selectStatusById(String id);

    List<WebMyAnswerVo> selectWebMyAnswerVo(String id);
}





package com.tang.newcloud.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.service.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.web.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.geom.IllegalPathStateException;
import java.util.List;
import java.util.Map;

/**
* @author 29878
* @description 针对表【edu_comment(评论)】的数据库操作Service
* @createDate 2023-01-06 14:07:48
*/
public interface CommentService extends IService<Comment> {

    Map getCommentList(Long page, Long limit, WebCommentQueryVo webCommentQueryVo);

    WebCommentIndexVo getOneComment(Long id,HttpServletRequest request);

    List<WebCommentVo> getComments(Long parentId,HttpServletRequest request);

    Boolean saveComment(Comment comment, JwtInfo jwtInfo);

    Boolean removeCommentById(Long id,String memberId);

    Boolean updateGoodNumber(Long id, String loginMemberId);

    List<WebCommentHotVo> getHotComment();

    List<WebCommentTagsVo> getTags();

    WebCommentBestAskVo getBestAsk(Long id, HttpServletRequest request);

    List<WebCommentVo> getSecondDataComments(Long parentId,HttpServletRequest request);
}

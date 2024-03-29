package com.tang.newcloud.service.edu.mapper;

import com.tang.newcloud.service.edu.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tang.newcloud.service.edu.entity.vo.SubjectVo;
import com.tang.newcloud.service.edu.entity.vo.WebSubjectSelectVo;
import com.tang.newcloud.service.edu.entity.vo.web.WebCommentTagsVo;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    List<SubjectVo> selectNestedListByParentId(String parentId);

    String selectSubjectNameById(String subjectId);

    List<WebCommentTagsVo> selectSubjects();

    List<WebSubjectSelectVo> selectIdAndTitle(String title);
}

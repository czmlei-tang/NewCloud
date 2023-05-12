package com.tang.newcloud.service.edu.service;

import com.tang.newcloud.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tang.newcloud.service.edu.entity.vo.SubjectVo;
import com.tang.newcloud.service.edu.entity.vo.WebSubjectSelectVo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
public interface SubjectService extends IService<Subject> {

    void batchImport(InputStream inputStream);

    List<SubjectVo> nestedList();

    List<WebSubjectSelectVo> getLikeSubjectData(String title);
}

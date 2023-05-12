package com.tang.newcloud.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.tang.newcloud.service.edu.entity.Subject;
import com.tang.newcloud.service.edu.entity.excel.ExcelSubjectData;
import com.tang.newcloud.service.edu.entity.vo.SubjectVo;
import com.tang.newcloud.service.edu.entity.vo.WebSubjectSelectVo;
import com.tang.newcloud.service.edu.listener.ExcelSubjectDataListener;
import com.tang.newcloud.service.edu.mapper.SubjectMapper;
import com.tang.newcloud.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tanglei
 * @since 2022-10-26
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchImport(InputStream inputStream) {

        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(subjectMapper))
                .excelType(ExcelTypeEnum.XLSX).sheet().doRead();

    }

    @Override
    public List<SubjectVo> nestedList() {
        return subjectMapper.selectNestedListByParentId("0");
    }

    @Override
    public List<WebSubjectSelectVo> getLikeSubjectData(String title) {
        if(title == null){
            title = "";
        }
        return subjectMapper.selectIdAndTitle(title);
    }
}

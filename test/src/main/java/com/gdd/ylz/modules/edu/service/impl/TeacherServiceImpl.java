package com.gdd.ylz.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.modules.edu.dao.TeacherMapper;
import com.gdd.ylz.modules.edu.dto.TeacherQueryDTO;
import com.gdd.ylz.modules.edu.entity.Teacher;
import com.gdd.ylz.modules.edu.service.ITeacherService;
import com.gdd.ylz.result.DataResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public DataResult saveTeacher(Teacher teacher) {
        this.save(teacher);
        return DataResult.success() ;
    }

    @Override
    public DataResult getListByPage(TeacherQueryDTO teacherQueryDTO) {
        Page page=new Page(teacherQueryDTO.getStart(),teacherQueryDTO.getLength());
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper=new LambdaQueryWrapper<Teacher>();
        if(StringUtils.isNotEmpty(teacherQueryDTO.getName())){
            lambdaQueryWrapper .like(Teacher::getName,teacherQueryDTO.getName());
        }
        return DataResult.success(this.page(page,lambdaQueryWrapper));
    }
}

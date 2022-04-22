package com.gdd.ylz.modules.edu.service;

import com.gdd.ylz.modules.edu.dto.TeacherQueryDTO;
import com.gdd.ylz.modules.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 讲师表 服务类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
public interface ITeacherService extends IService<Teacher> {
    
    DataResult saveTeacher(Teacher teacher);

    DataResult getListByPage(TeacherQueryDTO teacherQueryDTO);
}

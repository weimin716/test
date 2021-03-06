package com.gdd.ylz.modules.edu.service;

import com.gdd.ylz.modules.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.edu.request.CourseAddRequest;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
public interface ICourseService extends IService<Course> {

    void existName(CourseAddRequest courseAddRequest);

    void checkDeleteAble(String id);
}

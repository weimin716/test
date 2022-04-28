package com.gdd.ylz.modules.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdd.ylz.modules.edu.entity.CoursePart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.edu.request.CoursePartQueryRequest;

/**
 * <p>
 * 课程课时表 服务类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
public interface ICoursePartService extends IService<CoursePart> {

    IPage<CoursePart> coursePartPage(CoursePartQueryRequest coursePartQueryRequest);
}

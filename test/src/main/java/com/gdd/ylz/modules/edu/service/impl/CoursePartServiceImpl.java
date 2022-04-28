package com.gdd.ylz.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.edu.entity.CoursePart;
import com.gdd.ylz.modules.edu.dao.CoursePartMapper;
import com.gdd.ylz.modules.edu.request.CoursePartQueryRequest;
import com.gdd.ylz.modules.edu.service.ICoursePartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程课时表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@Service
public class CoursePartServiceImpl extends ServiceImpl<CoursePartMapper, CoursePart> implements ICoursePartService {

    @Override
    public IPage<CoursePart> coursePartPage(CoursePartQueryRequest coursePartQueryRequest) {
        Page page=new Page(coursePartQueryRequest.getStart(),coursePartQueryRequest.getLength());
        LambdaQueryWrapper<CoursePart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CoursePart::getCourseId,coursePartQueryRequest.getCourseId());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(coursePartQueryRequest.getName()),CoursePart::getName,coursePartQueryRequest.getName());
        return this.page(page,lambdaQueryWrapper);
    }
}

package com.gdd.ylz.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.modules.edu.entity.Category;
import com.gdd.ylz.modules.edu.entity.Course;
import com.gdd.ylz.modules.edu.dao.CourseMapper;
import com.gdd.ylz.modules.edu.request.CourseAddRequest;
import com.gdd.ylz.modules.edu.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {


    @Override
    public void existName(CourseAddRequest courseAddRequest) {
        //同一类别下课程名字不能相同
        LambdaQueryWrapper<Course> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getName,courseAddRequest.getName());
        lambdaQueryWrapper.eq(Course::getCategoryId,courseAddRequest.getCategoryId());
        if(StringUtils.isNotEmpty(courseAddRequest.getId())){
            lambdaQueryWrapper.ne(Course::getId,courseAddRequest.getId());
        }
        int count = this.count(lambdaQueryWrapper);
        if(count>0){
            throw new BusinessException(-1,"该课程分类下已有相同名称的课程");
        }
    }

    @Override
    public void checkDeleteAble(String id) {

    }
}

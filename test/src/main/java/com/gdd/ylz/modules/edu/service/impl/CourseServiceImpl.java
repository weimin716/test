/*
package com.gdd.ylz.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.modules.edu.entity.Category;
import com.gdd.ylz.modules.edu.entity.Course;
import com.gdd.ylz.modules.edu.dao.CourseMapper;
import com.gdd.ylz.modules.edu.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

*/
/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 *//*

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Override
    public void existName(Course course) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<Category>().eq(Category::getPid,category.getPid()).eq(Category::getName,category.getName());
        if(StringUtils.isNotEmpty(category.getId())){
            lambdaQueryWrapper.ne(Category::getId,category.getId());
        }

        List<Category> list = this.list(lambdaQueryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException(-1,"该节点下已存在该名称的分类");
        }
    }
}
*/

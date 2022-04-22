/*
package com.gdd.ylz.modules.edu.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.edu.entity.Course;
import com.gdd.ylz.modules.edu.service.ICourseService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

*/
/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 *//*

@RestController
@RequestMapping("/edu/course")
@Api(tags = {"edu"},description = "课程模块")
public class CourseController extends UserController {
    @Autowired
    private ICourseService courseService;

    @PostMapping("/saveCourse")
    @ApiOperation(value = "新增课程分类", notes = "新增课程分类")
    public DataResult saveCourse(@RequestBody Course Course){
        courseService.existName(Course);
        Course.setCreateBy(getUserId());
        Course.setUpdateBy(getUserId());
        Course.setId(StringUtils.getGUID());
        //排序
        courseService.save(Course);
        return DataResult.success();
    }

    @DeleteMapping("/deleteCourse/{id}")
    @ApiOperation(value = "删除课程分类", notes = "删除课程分类")
    @Transactional
    public DataResult deleteCourse(@PathVariable("id") String id){
        courseService.checkDeleteAble(id);
        courseService.update(new LambdaUpdateWrapper<Course>().set(Course::getIFlag, Constant.FLAG_NO).eq(Course::getId,id));
        return DataResult.success();
    }

    @DeleteMapping ("/deleteCourseWithDown/{id}")
    @ApiOperation(value = "删除课程分类及子节点", notes = "删除课程分类及子节点")
    @Transactional
    public DataResult deleteCourseWithDown(@PathVariable("id") String id){
        CourseService.update(new LambdaUpdateWrapper<Course>().set(Course::getIFlag, Constant.FLAG_NO).eq(Course::getId,id));
        CourseService.update(new LambdaUpdateWrapper<Course>().set(Course::getIFlag, Constant.FLAG_NO).eq(Course::getPid,id));
        return DataResult.success();
    }
    @PutMapping ("/updateCourse")
    @ApiOperation(value = "删除课程分类", notes = "删除课程分类")
    public DataResult updateCourse(@RequestBody Course Course){
        CourseService.existName(Course);
        CourseService.updateById(Course);
        return DataResult.success();
    }

}

*/

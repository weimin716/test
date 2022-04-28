package com.gdd.ylz.modules.edu.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.edu.base.OperationType;
import com.gdd.ylz.modules.edu.entity.Course;
import com.gdd.ylz.modules.edu.entity.CoursePart;
import com.gdd.ylz.modules.edu.request.CourseAddRequest;
import com.gdd.ylz.modules.edu.service.ICoursePartService;
import com.gdd.ylz.modules.edu.service.ICourseService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author weimin
 * @date 2022/4/25 0025 9:49
 * @param 
 * @return 
 * @Version1.0
 */


@RestController
@RequestMapping("/edu/course")
@Api(tags = {"edu"},description = "课程模块")
public class CourseController extends UserController {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private ICoursePartService coursePartService;

    @PostMapping("/saveCourse")
    @ApiOperation(value = "新增课程", notes = "新增课程")
    public DataResult saveCourse(@RequestBody @Validated(OperationType.Insert.class) CourseAddRequest courseAddRequest){
        courseService.existName(courseAddRequest);
        Course course=new Course();
        BeanUtils.copyProperties(courseAddRequest,course);
        course.setCreateBy(getUserId());
        course.setUpdateBy(getUserId());
        course.setId(StringUtils.getGUID());
        //排序
        courseService.save(course);
        return DataResult.success();
    }

    @DeleteMapping("/deleteCourse/{id}")
    @ApiOperation(value = "删除课程", notes = "删除课程")
    @Transactional
    public DataResult deleteCourse(@PathVariable("id") String id){
        courseService.checkDeleteAble(id);
        courseService.update(new LambdaUpdateWrapper<Course>().set(Course::getIFlag, Constant.FLAG_NO).eq(Course::getId,id));
        coursePartService.update(new LambdaUpdateWrapper<CoursePart>().set(CoursePart::getIFlag,Constant.FLAG_NO).eq(CoursePart::getCourseId,id));
        return DataResult.success();
    }

    @PutMapping ("/updateCourse")
    @ApiOperation(value = "更新课程", notes = "更新课程")
    public DataResult updateCourse(@RequestBody CourseAddRequest courseAddRequest){
        courseService.existName(courseAddRequest);
        Course course=new Course();
        BeanUtils.copyProperties(courseAddRequest,course);
        courseService.updateById(course);
        return DataResult.success();
    }

    @PutMapping ("/detail/{id}")
    @ApiOperation(value = "更新课程", notes = "更新课程")
    public DataResult detailCourse(@PathVariable("id") String id){
       return DataResult.success(courseService.getById(id));
    }

}


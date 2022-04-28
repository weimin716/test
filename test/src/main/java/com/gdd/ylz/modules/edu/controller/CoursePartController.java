package com.gdd.ylz.modules.edu.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.edu.base.OperationType;
import com.gdd.ylz.modules.edu.entity.CoursePart;
import com.gdd.ylz.modules.edu.request.CoursePartAddRequest;
import com.gdd.ylz.modules.edu.request.CoursePartQueryRequest;
import com.gdd.ylz.modules.edu.service.ICoursePartService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程课时表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/edu/course-part")
@Api(tags = {"edu"},description = "课程课时模块")
public class CoursePartController {

    @Autowired
    private ICoursePartService coursePartService;

    @GetMapping("/page")
    @ApiOperation(value = "课程课时分页", notes = "课程课时分页")
    public DataResult coursePartPage(@RequestBody @Validated(OperationType.Page.class) CoursePartQueryRequest coursePartQueryRequest){
       return DataResult.success(coursePartService.coursePartPage(coursePartQueryRequest));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "删除课时", notes = "删除课时")
    public DataResult coursePartDetail(@PathVariable("id")String id){
        return DataResult.success(coursePartService.getById(id));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增课时", notes = "新增课时")
    public DataResult coursePartAdd(@RequestBody CoursePartAddRequest coursePartAddRequest){

    }

    @GetMapping("/delete/{id}")
    public DataResult coursePartDelete(@PathVariable("id")String id){
        coursePartService.update(new LambdaUpdateWrapper<CoursePart>().set(CoursePart::getIFlag, Constant.FLAG_NO).eq(CoursePart::getId,id));
        return DataResult.success();
    }


}


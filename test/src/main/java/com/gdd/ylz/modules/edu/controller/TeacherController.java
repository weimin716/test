package com.gdd.ylz.modules.edu.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.edu.dto.TeacherQueryDTO;
import com.gdd.ylz.modules.edu.entity.Teacher;
import com.gdd.ylz.modules.edu.service.ITeacherService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/edu/teacher")
@Api(tags = {"edu"},description = "讲师模块")
public class TeacherController extends UserController {
    @Autowired
    private ITeacherService teacherService;

    @PostMapping("/saveTeacher")
    @ApiOperation(value = "新增讲师", notes = "新增讲师")
    public DataResult saveTask(@RequestBody Teacher teacher){
        teacher.setCreateBy(getUserId());
        teacher.setUpdateBy(getUserId());
        return teacherService.saveTeacher(teacher);
    }
    @GetMapping("/listByPage")
    @ApiOperation(value="讲师列表分页",notes="讲师列表分页")
    public DataResult listTeacherByPage(TeacherQueryDTO teacherQueryDTO){
        return teacherService.getListByPage(teacherQueryDTO);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除讲师",notes="删除讲师")
    public DataResult deleteTeacher(@PathVariable("id")String id){
          teacherService.update(new UpdateWrapper<Teacher>().lambda().set(Teacher::getIFlag,0));
          return DataResult.success();
    }
    @GetMapping("/getdetail/{id}")
    @ApiOperation(value="获得讲师详情",notes="获得讲师详情")
    public DataResult getDetail(@PathVariable("id")String id){
       return DataResult.success(teacherService.getById(id));
    }

    @PutMapping("/update")
    @ApiOperation(value="修改讲师",notes="修改讲师")
    public DataResult updateTeacher(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return DataResult.success();
    }



}


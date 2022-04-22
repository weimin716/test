package com.gdd.ylz.modules.edu.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.edu.entity.Category;
import com.gdd.ylz.modules.edu.service.ICategoryService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程分类表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/edu/category")
@Api(tags = {"edu"},description = "讲师模块")
public class CategoryController extends UserController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/saveCategory")
    @ApiOperation(value = "新增课程分类", notes = "新增课程分类")
    public DataResult saveCategory(@RequestBody Category category){
        categoryService.existName(category);
        category.setCreateBy(getUserId());
        category.setUpdateBy(getUserId());
        category.setId(StringUtils.getGUID());
        //排序
        categoryService.insertCategory(category);
        return DataResult.success();
    }

    @DeleteMapping ("/deleteCategory/{id}")
    @ApiOperation(value = "删除课程分类", notes = "删除课程分类")
    @Transactional
    public DataResult deleteCategory(@PathVariable("id") String id){
        categoryService.checkDeleteAble(id);
        categoryService.update(new LambdaUpdateWrapper<Category>().set(Category::getIFlag, Constant.FLAG_NO).eq(Category::getId,id));
        categoryService.reShuttleOrder(id);
        return DataResult.success();
    }

    @DeleteMapping ("/deleteCategoryWithDown/{id}")
    @ApiOperation(value = "删除课程分类及子节点", notes = "删除课程分类及子节点")
    @Transactional
    public DataResult deleteCategoryWithDown(@PathVariable("id") String id){
        categoryService.update(new LambdaUpdateWrapper<Category>().set(Category::getIFlag, Constant.FLAG_NO).eq(Category::getId,id));
        categoryService.update(new LambdaUpdateWrapper<Category>().set(Category::getIFlag, Constant.FLAG_NO).eq(Category::getPid,id));
        categoryService.reShuttleOrder(id);
        return DataResult.success();
    }
    @PutMapping ("/updateCategory")
    @ApiOperation(value = "删除课程分类", notes = "删除课程分类")
    public DataResult updateCategory(@RequestBody Category category){
        categoryService.existName(category);
        categoryService.updateById(category);
        return DataResult.success();
    }

}


package com.gdd.ylz.modules.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.edu.entity.Category;
import com.gdd.ylz.modules.edu.dao.CategoryMapper;
import com.gdd.ylz.modules.edu.request.CategoryQueryRequest;
import com.gdd.ylz.modules.edu.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 课程分类表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void checkDeleteAble(String id) {
        List<Category> list = this.list(new LambdaQueryWrapper<Category>().eq(Category::getPid, id).eq(Category::getIFlag, Constant.FLAG_YES));
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException(-1,"该分类下,有子级分类，是否确认删除？");
        }
    }

    @Override
    public void existName(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<Category>().eq(Category::getPid,category.getPid()).eq(Category::getName,category.getName());
        if(StringUtils.isNotEmpty(category.getId())){
            lambdaQueryWrapper.ne(Category::getId,category.getId());
        }

        List<Category> list = this.list(lambdaQueryWrapper);
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException(-1,"该节点下已存在该名称的分类");
        }
    }

    @Override
    public void reShuttleOrder(String id) {
        Category category = this.getById(id);
        this.update(new LambdaUpdateWrapper<Category>().setSql("order_num=order_num-1").eq(Category::getPid,category.getPid()).gt(Category::getOrderNum,category.getOrderNum()).eq(Category::getIFlag,Constant.FLAG_YES));
    }

    @Override
    public IPage<Category> categoryByPage(CategoryQueryRequest categoryQueryRequest) {
        Page page=new Page(categoryQueryRequest.getStart(),categoryQueryRequest.getLength());
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(categoryQueryRequest.getName()),Category::getName,categoryQueryRequest.getName()).eq(StringUtils.isNotEmpty(categoryQueryRequest.getPid()),Category::getPid,categoryQueryRequest.getPid());
        IPage<Category> categoryIPage = this.page(page, lambdaQueryWrapper);
        return categoryIPage;
    }
}

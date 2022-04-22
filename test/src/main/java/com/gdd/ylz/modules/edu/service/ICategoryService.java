package com.gdd.ylz.modules.edu.service;

import com.gdd.ylz.modules.edu.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程分类表 服务类
 * </p>
 *
 * @author xzg
 * @since 2022-04-18
 */
public interface ICategoryService extends IService<Category> {

    void insertCategory(Category category);

    void checkDeleteAble(String id);

    void existName(Category category);

    void reShuttleOrder(String id);
}

package com.gdd.ylz.modules.timer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdd.ylz.modules.timer.dto.SysTaskQueryDTO;
import com.gdd.ylz.modules.timer.entity.SysTask;
import com.gdd.ylz.result.DataResult;

/**
 * <p>
 * 定时任务信息表 服务类
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
public interface ISysTaskService extends IService<SysTask> {



    /**
     * 分页查询.
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.springboot.demo.entity.Task>
     * @since 1.0.0
     */
    IPage<SysTask> getListByPage(SysTaskQueryDTO sysTaskQueryDTO);

    /**
     * 初始化加载任务.
     *
     * @since 1.0.0
     */
    void initSchedule();

    /**
     * 修改一个任务.
     *
     * @param task :任务信息
     * @return boolean
     * @since 1.0.0
     */
    DataResult updateTaskById(SysTask task);

    /**
     * 根据id删除一个任务.
     *
     * @param id :
     * @return boolean
     * @since 1.0.0
     */
    DataResult deleteById(String id);

    /**
     * 根据id执行一次任务.
     *
     * @param id :id
     * @return boolean
     * @since 1.0.0
     */
    DataResult performOneById(String id);

    /**
     * 根据id和状态判断执行或暂停一个任务.
     *
     * @param id     : id
     * @param status :状态 0为执行  1为暂停
     * @return boolean
     * @since 1.0.0
     */
    DataResult performOrSuspendOneById(String id, Integer status);

    /**
     * 保存定时任务.
     * @param task :
     * @return java.lang.Boolean
     * @since 1.0.0
     */
    DataResult saveTask(SysTask task);

     /**
      *  @author: weimin
      *  @Date: 2022/4/18 14:47
      *  @Description:
      */ 
     
    void checkExist(SysTask task);

    DataResult getDetailById(String id);
}

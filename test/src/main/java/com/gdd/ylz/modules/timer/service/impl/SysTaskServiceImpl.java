package com.gdd.ylz.modules.timer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.modules.timer.dao.SysTaskMapper;
import com.gdd.ylz.modules.timer.dto.SysTaskQueryDTO;
import com.gdd.ylz.modules.timer.entity.SysTask;
import com.gdd.ylz.modules.timer.quartz.QuartzManager;
import com.gdd.ylz.modules.timer.service.ISysTaskService;
import com.gdd.ylz.result.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 定时任务信息表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
@Service
@Slf4j
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements ISysTaskService {
    @Resource
    private SysTaskMapper sysTaskMapper;
    @Resource
    private QuartzManager quartzManager;


    @Override
    public IPage<SysTask> getListByPage(SysTaskQueryDTO sysTaskQueryDTO) {
        Page page=new Page(sysTaskQueryDTO.getStart(),sysTaskQueryDTO.getLength());
        return sysTaskMapper.getListByPage(page,sysTaskQueryDTO);
    }

    @Override
    public void initSchedule() {
        List<SysTask> list = this.list();
        for (SysTask task : list) {
            quartzManager.addJob(task);
        }


    }

    @Override
    public DataResult updateTaskById(SysTask task) {
        SysTask oldTask = this.getById(task.getId());
        if (this.updateById(task)) {
            try {
                quartzManager.deleteJob(oldTask);
                quartzManager.addJob(task);
                return DataResult.success();
            } catch (SchedulerException e) {
                log.error("更新job任务表达式异常：{}", e.getMessage());
                return DataResult.error();
            }
        }
        return DataResult.error();

    }

    @Override
    public DataResult deleteById(String id) {
        SysTask task = this.getById(id);
        if (task.getStatus() == 0) {
            try {
                quartzManager.deleteJob(task);
            } catch (SchedulerException e) {
                log.error("删除任务异常：{}", e.getMessage());
                return DataResult.error();
            }
        }
        this.removeById(id);
        return DataResult.success();
    }

    @Override
    public DataResult performOneById(String id) {
        SysTask task = this.getById(id);
        try {
            quartzManager.runJobNow(task);
            return DataResult.success();
        } catch (SchedulerException e) {
            log.error("立即执行任务异常：{}", e.getMessage());
            return DataResult.error();
        }
    }

    @Override
    public DataResult performOrSuspendOneById(String id, Integer status) {
        SysTask task = this.getById(id);
        task.setStatus(status);
        try {
            if (status == 1) {
                quartzManager.pauseJob(task);
            }
            if (status == 0) {
                quartzManager.resumeJob(task);
            }
        } catch (SchedulerException ex) {
            log.error("修改任务状态异常：{}", ex.getMessage());
            return DataResult.error();
        }
        this.updateById(task);
        return DataResult.success();
    }

    @Override
    public DataResult saveTask(SysTask task) {
        if (this.save(task)) {
            quartzManager.addJob(task);
            return DataResult.success();
        }
        return DataResult.error();

    }

    @Override
    public void checkExist(SysTask task) {
        if(sysTaskMapper.checkExistJobName(task)>0) {
            throw new BusinessException(-1,"已存在该分组下的任务");
        }
        if(sysTaskMapper.checkExistBeanName(task)>0){
            throw new BusinessException(-1,"已存在该类下的方法");
        }
        if(!CronExpression.isValidExpression(task.getCron())){
            throw new BusinessException(-1,"cron表达式不可用");
        }
    }

    @Override
    public DataResult getDetailById(String id) {
        return DataResult.success(this.getById(id));
    }
}

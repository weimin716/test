package com.gdd.ylz.modules.timer.controller;


import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.timer.dto.SysTaskQueryDTO;
import com.gdd.ylz.modules.timer.entity.SysTask;
import com.gdd.ylz.modules.timer.service.ISysTaskService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 定时任务信息表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2022-04-01
 */
@RestController
@RequestMapping("/sys/timer/sys-task")
@Api(tags={"sys"})
public class SysTaskController extends UserController {
    @Autowired
    private ISysTaskService sysTaskService;

    @PostMapping("/saveTask")
    @ApiOperation(value = "新增任务", notes = "新增任务")
    public DataResult saveTask(@RequestBody SysTask task){
        sysTaskService.checkExist(task);
        task.setCreatedBy(getUserId());
        task.setUpdatedBy(getUserId());
        return sysTaskService.saveTask(task);
    }

    @GetMapping("/selecttaskbypage")
    @ApiOperation(value = "分页查询任务列表", notes = "分页查询任务列表")
    public DataResult selectTaskByPage(SysTaskQueryDTO sysTaskQueryDTO){
        return DataResult.success(sysTaskService.getListByPage(sysTaskQueryDTO));
    }

    @PutMapping("/updatetaskById")
    @ApiOperation(value = "更新任务", notes = "更新任务")
    public DataResult updatetaskById(@RequestBody SysTask sysTask){
        sysTaskService.checkExist(sysTask);
        return sysTaskService.updateTaskById(sysTask);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除任务", notes = "删除任务")
    public DataResult deleteById(@RequestBody SysTask task){
        return sysTaskService.deleteById(task.getId());
    }

    @GetMapping("/performOneById/{id}")
    @ApiOperation(value="立即执行某个任务",notes="立即执行某个任务")
    public DataResult performOneById(@PathVariable("id")String id){
        return sysTaskService.performOneById(id);
    }

    @PostMapping("/performOrSuspendOneById")
    @ApiOperation(value="启停某个任务",notes="启停某个任务")
    public DataResult performOrSuspendOneById(@RequestBody SysTask task){
        return sysTaskService.performOrSuspendOneById(task.getId(),task.getStatus());
    }

    @GetMapping("/getDetailById/{id}")
    @ApiOperation(value="启停某个任务",notes="启停某个任务")
    public DataResult getDetailById(@PathVariable("id") String id){
        return sysTaskService.getDetailById(id);
    }



}


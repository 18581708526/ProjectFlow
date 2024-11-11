package com.lzj.workflow.common.controller;


import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.common.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    @PostMapping("add/{day}/{studentUser}")
    public AjaxResult sub(@PathVariable("day") Integer day, @PathVariable("studentUser") String studentUser) {
        // 学生提交请假申请
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        map.put("studentName", studentUser);

        repositoryService.createDeployment().addClasspathResource("process/act_qjlc.bpmn20.xml").deploy();
        // stuLeave为学生请假流程xml文件中的id，bpmn.xml文件的process id="stuLeave"
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("stuLeave", map);
        log.info("流程实例ID：" + processInstance.getId());
        //完成申请任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        //此处id为流程id
        System.out.println("提交成功.流程Id为：" + processInstance.getId());
        return new AjaxResult(true, "提交成功.流程Id为：" + processInstance.getId());
    }

    @GetMapping("teacherList")
    public AjaxResult teacherList() {
        //此处.taskCandidateGroup("a")的值为流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("startEvent1").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        log.info("任务列表：" + tasks);
        if (tasks == null || tasks.size() == 0) {
            return new AjaxResult(false, "没有任务");
        }
        return new AjaxResult(true, "获取成功", taskVOList);
    }

    /**
     * 辅导员批准
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherApply/{taskId}")
    public AjaxResult teacherApply(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        if (task == null) {
            return new AjaxResult(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new AjaxResult(true, "审批成功");
    }

    /**
     * 辅导员拒绝
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherReject/{taskId}")
    public AjaxResult teacherReject(@PathVariable("taskId") String taskId) {
        Task task1 = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task1 == null) {
            return new AjaxResult(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(task1.getId(), map);
        return new AjaxResult(true, "审批失败");
    }

    /**
     * 院长获取审批管理列表
     */
    @GetMapping("deanList")
    public AjaxResult deanList() {
        //此处.taskCandidateGroup("b")的值“b”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        if (tasks == null || tasks.size() == 0) {
            return new AjaxResult(false, "没有任务");
        }
        return new AjaxResult(true, "获取成功", taskVOList);
    }

    /**
     * 院长批准
     *
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanApply/{taskId}")
    public AjaxResult apply(@PathVariable("taskId") String taskId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (tasks == null) {
            return new AjaxResult(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        for (Task task : tasks) {
            taskService.complete(task.getId(), map);
        }
        return new AjaxResult(true, "审批成功");
    }

    /**
     * 院长拒绝
     *
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanReject/{taskId}")
    public AjaxResult deanReject(@PathVariable("taskId") String taskId) {
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return new AjaxResult(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new AjaxResult(true, "审批成功");
    }

    /**
     * 再次申请
     *
     * @param piId 流程id
     * @param day
     * @return
     */
    @GetMapping("subAgain/{piId}/{day}")
    public AjaxResult subAgain(@PathVariable("piId") String piId, @PathVariable("day") Integer day) {
        Task task = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        if (Objects.isNull(task)) {
            return new AjaxResult(false, "没有任务");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        taskService.complete(task.getId(), map);
        return new AjaxResult(true, "申请成功");
    }

}

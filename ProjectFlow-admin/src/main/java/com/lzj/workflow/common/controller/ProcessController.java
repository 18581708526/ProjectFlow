package com.lzj.workflow.common.controller;


import com.lzj.workflow.common.vo.ProcessInstanceVO;
import com.lzj.workflow.common.vo.QjProcessVo;
import com.lzj.workflow.common.vo.TaskVO;
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;
import com.lzj.workflow.myinitprocess.service.IWfMyinitiprocessService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/process")
public class ProcessController {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;
    @Autowired
    private IWfMyinitiprocessService wfMyinitiprocessService;
    @GetMapping("/my-initiated")
    public List<ProcessInstanceVO> getMyInitiatedProcesses(@RequestParam String userId) {


        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .startedBy(userId)
                .list();

        List<ProcessInstanceVO> processInstanceVOS = new ArrayList<>();
        for (ProcessInstance processInstance : processInstances) {
            processInstanceVOS.add(new ProcessInstanceVO(
                    processInstance.getId(),
                    processInstance.getProcessDefinitionId(),
                    processInstance.getBusinessKey(),
                    processInstance.getStartUserId(),
                    processInstance.getStartTime()
            ));
        }


        return processInstanceVOS;
    }

    @GetMapping("/my-todo")
    public List<TaskVO> getMyTodoTasks(@RequestParam String userId) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userId)
                .list();

        List<TaskVO> taskVOS = new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            taskVOS.add(new TaskVO(
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    new QjProcessVo(
                            variables.get("leaveTask").toString(),
                            (Integer) variables.get("day"),
                            variables.get("reason").toString(),
                            task.getTaskDefinitionKey()
                    )
            ));
        }
        return taskVOS;
    }


    @GetMapping("/current-task-users")
    public Set<String> getCurrentTaskUsers() {
        List<Task> tasks = taskService.createTaskQuery().list();
        Set<String> userIds = new HashSet<>();

        for (Task task : tasks) {
            if (task.getAssignee() != null) {
                userIds.add(task.getAssignee());
            }
        }

        return userIds;
    }
    @GetMapping("/my-completed")
    public List<TaskVO> getMyCompletedTasks(@RequestParam String userId) {
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished()
                .list();

        List<TaskVO> taskVOS = new ArrayList<>();
        for (HistoricTaskInstance historicTask : historicTasks) {
            List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery()
                .taskId(historicTask.getId())
                .list();

            Map<String, Object> variableMap = variables.stream()
                .collect(Collectors.toMap(HistoricVariableInstance::getVariableName, HistoricVariableInstance::getValue));
            taskVOS.add(new TaskVO(
                historicTask.getId(),
                historicTask.getName(),
                historicTask.getDescription(),
                new QjProcessVo(
                        variableMap.get("leaveTask").toString(),
                        (Integer) variableMap.get("day"),
                        variableMap.get("reason").toString(),
                        historicTask.getTaskDefinitionKey()
                )
            ));
        }
        return taskVOS;
    }
}

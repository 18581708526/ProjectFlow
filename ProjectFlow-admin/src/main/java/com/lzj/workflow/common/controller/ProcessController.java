package com.lzj.workflow.common.controller;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.common.service.IProcessService;
import com.lzj.workflow.common.vo.ModelProVo;
import com.lzj.workflow.common.vo.ProcessInstanceVO;
import com.lzj.workflow.common.vo.QjProcessVo;
import com.lzj.workflow.common.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ModelEntity;
import org.flowable.engine.impl.persistence.entity.data.ModelDataManager;

import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.lzj.common.core.domain.AjaxResult.success;


@Slf4j
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
    private IProcessService processService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;




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
                            variables.get("day").toString(),
                            variables.get("reason").toString(),
                            task.getTaskDefinitionKey(),"1"
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
                        variableMap.get("day").toString(),
                        variableMap.get("reason").toString(),
                        historicTask.getTaskDefinitionKey(),"1"
                )
            ));
        }
        return taskVOS;
    }
    /**
     *部署流程接口
     * @param modelId
     */
    @GetMapping("/deployPro/{modelId}")
    public AjaxResult deployPro(@PathVariable("modelId")String modelId) {
      return processService.deployPro(modelId);
    }

    @GetMapping("/getModelResource/{modelId}")
    public AjaxResult getModelResource(@PathVariable("modelId")String modelId) {
        return success("获取成功",processService.getModelResource(modelId));}
    /**
     * 更新模型方法测试
     */

    @GetMapping("/updateModel")
    public String updateModel(@RequestParam String modelId, @RequestParam String bpmnXml) {
//        byte[] source = repositoryService.getModelEditorSource(modelId);
//        log.info("xml文件：{}",new String(source, StandardCharsets.UTF_8));

        Model repositoryServiceModel = repositoryService.getModel(modelId);
        List<Model> list1 = repositoryService.createModelQuery().modelId(modelId).list();
        List<Model> list = repositoryService.createNativeModelQuery().list();
        for (Model model : list) {
            log.info("model:{}",model.getId());
        }

        try {
            ModelEntity model = getModelFromDesigner(modelId);
            if (model == null) {
                throw new RuntimeException("Model not found with id: " + modelId);
            }

            // 更新模型的 BPMN XML 内容
            repositoryService.addModelEditorSource(model.getId(), bpmnXml.getBytes());

            return "Model updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to update model: " + e.getMessage();
        }
    }

    private ModelEntity getModelFromDesigner(String modelId) {
        ModelDataManager modelDataManager = ((ProcessEngineConfigurationImpl) processEngineConfiguration).getModelDataManager();
        return modelDataManager.findById(modelId);
    }

}

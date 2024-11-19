package com.lzj.workflow.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.common.vo.QjProcessVo;
import com.lzj.workflow.common.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Model;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lzj.common.core.domain.AjaxResult.success;

@Slf4j
@RestController
@RequestMapping("/model/test")
public class CreateModelController {
        @Autowired
        private TaskService taskService;
        @Autowired
        private RepositoryService repositoryService;
        /**
         * 查询我的待办数据
         */
        @GetMapping("/todo")
        @Transactional
        public AjaxResult selectMytodo(){

                List<Task> tasks = taskService.createTaskQuery().taskAssignee("lzj").list();
                List<Task> tasks1 = taskService.createTaskQuery().taskAssignee("1").list();
                tasks.addAll(tasks1);
                List<TaskVO> taskVOList = tasks.stream().map(task -> {
                        Map<String, Object> variables = taskService.getVariables(task.getId());
                        String leaveTask = Optional.ofNullable(variables.get("leaveTask")).map(Object::toString).orElse(null);
                        String day = Optional.ofNullable(variables.get("day")).map(Object::toString).orElse(null);
                        String reason = Optional.ofNullable(variables.get("reason")).map(Object::toString).orElse(null);
                        return new TaskVO(task.getId(), task.getName(), task.getDescription(),
                                new QjProcessVo(leaveTask, day, reason, task.getTaskDefinitionKey(), "1"));
                }).collect(Collectors.toList());
                log.info("任务列表：" + tasks);
                if (tasks == null || tasks.size() == 0) {
                        return success("没有任务");
                }
                return success("查询成功",taskVOList);
        }

        /**
         * 查询模型管理列表
         */
        @GetMapping("/test")
        public AjaxResult list() {
                Model model = repositoryService.newModel();
                try {
                // 设置模型的基本信息
                String name = "My New Process";
                String description = "This is a new process model created programmatically";
                int version = 1;
                String key = "myNewProcess";

                model.setName(name);
                model.setKey(key);
                model.setCategory("0");

                Map<String, Object> metaInfo = new HashMap<>();
                metaInfo.put("description", description);
                model.setMetaInfo(new ObjectMapper().writeValueAsString(metaInfo));
                model.setVersion(version);
                // 保存模型到数据库
                repositoryService.saveModel(model);
                // 可以进一步设置模型的XML内容或其他属性
                // 例如，设置模型的JSON内容
                String jsonNode = "{ \"name\": \"" + name + "\", \"properties\": { \"process_id\": \"" + key + "\" } }";
                repositoryService.addModelEditorSource(model.getId(), jsonNode.getBytes());

                System.out.println("流程模型已创建，模型ID: " + model.getId());
                } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                }
                return success("添加成功",model.getId());
        }
}

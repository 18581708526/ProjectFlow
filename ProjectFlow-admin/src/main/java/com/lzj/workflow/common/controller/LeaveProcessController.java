package com.lzj.workflow.common.controller;

import com.lzj.common.core.domain.AjaxResult;
import com.lzj.common.core.domain.entity.SysUser;
import com.lzj.common.utils.SecurityUtils;
import com.lzj.workflow.common.util.TimeConvertUtil;
import com.lzj.workflow.common.vo.*;
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;
import com.lzj.workflow.myinitprocess.mapper.WfMyinitiprocessMapper;
import com.lzj.workflow.mytodoprocess.domain.WfMytodoprocess;
import com.lzj.workflow.mytodoprocess.mapper.WfMytodoprocessMapper;
import com.lzj.workflow.mytodoprocess.vo.RejectVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.jam.internal.elements.CommentImpl;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricDetail;
import org.flowable.engine.history.HistoricFormProperty;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.lzj.common.core.domain.AjaxResult.success;
import static com.lzj.workflow.mytodoprocess.menu.WorkFlowState.*;

@RestController
@RequestMapping("/leaveProcess")
@Slf4j
public class LeaveProcessController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private WfMyinitiprocessMapper wfMyinitiprocessMapper;
    @Autowired
    private WfMytodoprocessMapper wfMytodoprocessMapper;

    /**
     * 部署流程测试（与项目业务分开） 通过资源文件部署，不能用这种方法
     *
     * @return
     */
    @PostMapping("add")
    public AjaxResult addsub() {
        Deployment deploy = repositoryService
                .createDeployment()
                .addClasspathResource("process/LeaveProcess.bpmn20.xml")
                .deploy();
        String processDefinitionId = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult()
                .getId();
        return success("部署成功，流程部署ID:" + deploy.getId() + "流程定义ID：" + processDefinitionId);
    }

    @PostMapping("add/{studentUser}")
    public AjaxResult sub(@PathVariable("studentUser") String studentUser) {
        // 学生提交请假申请
        Map<String, Object> map = new HashMap<>();
        map.put("leaveTask", studentUser);
        // stuLeave为学生请假流程xml文件中的id，bpmn.xml文件的process id="stuLeave"
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveProcess", map);
        runtimeService.setVariables(processInstance.getId(), map);


        log.info("流程实例ID：" + processInstance.getId());
        //完成申请任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        //此处id为流程id
        return success("提交成功.流程ID为：" + processInstance.getId());
    }

    @PostMapping("add/stu")
    public AjaxResult substu(@RequestBody QjProcessVo qjProcessVo) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String userId = user.getUserId().toString();
        String userName = user.getUserName();
        // 学生提交请假申请
        Map<String, Object> map = new HashMap<>();
        map.put("leaveTask", qjProcessVo.getName());
        map.put("day", qjProcessVo.getDay());
        map.put("reason", qjProcessVo.getReason());
        map.put("userId", qjProcessVo.getApprover());
        map.put("fq_user", userId);
        Authentication.setAuthenticatedUserId(userId);

        // stuLeave为学生请假流程xml文件中的id，bpmn.xml文件的process id="stuLeave"
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(qjProcessVo.getKey(), map);
        runtimeService.setVariables(processInstance.getId(), map);
        log.info("流程实例ID：" + processInstance.getId());
        //完成申请任务
//        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//        taskService.complete(task.getId());


        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
        for(Task t:tasks){
            this.ProMyInitProcess(userId);
            this.ProMyTodoProcess(userId, userName, t.getAssignee());
        }



        //此处id为流程id
        Authentication.setAuthenticatedUserId(null);
        return success("提交成功.流程ID为：" + processInstance.getId());
    }

    @GetMapping("teacherList")
    public AjaxResult teacherList() {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("1").list();

        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());

            return new TaskVO(task.getId(), task.getName(), task.getDescription(),
                    new QjProcessVo(variables.get("leaveTask").toString(),
                            variables.get("day").toString(), variables.get("reason").toString(),
                            task.getTaskDefinitionKey(), "1"));

        }).collect(Collectors.toList());
        log.info("任务列表：" + tasks);
        if (tasks == null || tasks.size() == 0) {
            return success("没有任务");
        }
        return success("获取成功", taskVOList);
    }

    @GetMapping("deleteflow")
    public AjaxResult deleteFlow() {
        // 获取默认的流程引擎实例
        RepositoryService repositoryService = ProcessEngines.getDefaultProcessEngine().getRepositoryService();
        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        HistoryService historyService = ProcessEngines.getDefaultProcessEngine().getHistoryService();

        // 1. 删除所有运行中的流程实例
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance processInstance : processInstances) {
            runtimeService.deleteProcessInstance(processInstance.getId(), "删除所有流程实例");
        }

        // 2. 删除所有历史记录
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
        }

        // 3. 删除所有流程定义
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition processDefinition : processDefinitions) {
            repositoryService.deleteDeployment(processDefinition.getDeploymentId());
        }

        return success("所有流程实例和定义已成功删除");
    }

    @GetMapping("flowtype")
    public AjaxResult quaryFlowType() {
        // 获取默认的ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 查询所有流程定义
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .list();
        List<ProcessVo> processes = new ArrayList<>();
        // 打印每个流程定义的信息
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessVo processVo = new ProcessVo(
                    processDefinition.getKey(),
                    processDefinition.getName(),
                    processDefinition.getVersion(),
                    processDefinition.getDeploymentId(),
                    processDefinition.getResourceName());
            processes.add(processVo);
        }

        // 统计不同流程类型的数量
        int uniqueProcessDefinitionCount = processDefinitions.stream()
                .map(ProcessDefinition::getKey)
                .distinct()
                .toArray().length;
        System.out.println("流程类型数量为: " + uniqueProcessDefinitionCount);
        return success("查询成功", processes);

    }

    /**
     * 获取流程中变量
     */
    @GetMapping("getvariable/{taskid}")
    public AjaxResult getVariables(@PathVariable("taskid") String taskid) {
        Map<String, Object> map = taskService.getVariables(taskid);
        return success("获取成功", map);
    }

    /**
     * 辅导员批准
     *
     * @param taskId
     */
    @GetMapping("teacherApply/{taskId}")
    public AjaxResult teacherApply(@PathVariable("taskId") String taskId) {

        //Task task = taskService.createTaskQuery().taskCandidateGroup("jysh").taskId(taskId).singleResult();
        String userId = SecurityUtils.getUserId().toString();
        String userName = SecurityUtils.getUsername().toString();
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskId(taskId).singleResult();
        if (task == null) {
            return success("没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("checkResult", "通过");
        taskService.complete(task.getId(), map);
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
        for(Task t:tasks){
            this.ProMyInitProcess(userId);
            this.ProMyTodoProcess(userId, userName, t.getAssignee());
        }

        // 在这儿写审批通过后回写已办数据
        this.passProcessUpdate(taskId);

        return success("审批成功", new TaskVO(task.getId(), task.getName(), task.getDescription()));
    }

    /**
     * 辅导员拒绝
     *
     * @param rejectVo 拒绝对象
     */

    @PostMapping("teacherReject")
    public AjaxResult teacherReject(@RequestBody RejectVo rejectVo) {
        String userId = SecurityUtils.getUserId().toString();
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskId(rejectVo.getTaskId()).singleResult();
        if (task == null) {
            return success("没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("checkResult", "驳回");
        taskService.complete(task.getId(), map);
        //更新流程发起数据
        this.rejectProcessUpdate(rejectVo);
        return success("审批成功", new TaskVO(task.getId(), task.getName(), task.getDescription()));
    }

    /**
     * 院长获取审批管理列表
     */
//    @GetMapping("deanList")
//    public AjaxResult deanList() {
//        //此处.taskCandidateGroup("b")的值“b”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
//        List<TaskVO> taskVOList = tasks.stream().map(task -> {
//            Map<String, Object> variables = taskService.getVariables(task.getId());
//            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
//        }).collect(Collectors.toList());
//        if (tasks == null || tasks.size() == 0) {
//            return new AjaxResult(false, "没有任务");
//        }
//        return new AjaxResult(true, "获取成功", taskVOList);
//    }

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


    /**
     * 生成流程图
     *
     * @param taskId 流程ID
     */
    @GetMapping("processDiagram/{taskId}")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        // 从任务ID获取任务对象

        if (task == null) {
            log.error("任务ID {} 对应的任务不存在", taskId);
            return;
        }

        // 从任务对象中获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        if (processInstanceId == null) {
            log.error("任务ID {} 对应的流程实例ID为空", taskId);
            return;
        }

        // 使用流程实例ID查询流程实例
        if (pi == null) {
            log.error("流程实例ID {} 对应的流程实例不存在或已结束", processInstanceId);
            return;
        }

        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Idm
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png", activityIds, flows, engconf.getActivityFontName(),
                engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(),
                1.0, true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            //此处设置resp的header诸多文章都没写，但是我不写出不来流程图，诸位可去掉试试
            httpServletResponse.setHeader("Content-Type",
                    "image/png;charset=utf-8");
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获取正在执行的活动ID列表
     *
     * @param executions 执行对象列表
     * @return 活动ID列表
     */
    private List<String> getActiveActivityIds(List<Execution> executions) {
        List<String> activityIds = new ArrayList<>();
        for (Execution exe : executions) {
            activityIds.addAll(runtimeService.getActiveActivityIds(exe.getId()));
        }
        return activityIds;
    }

    public void ProMyInitProcess(String userId) {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .startedBy(userId)
                .list();
        WfMyinitiprocess myinitp = new WfMyinitiprocess();
        for (ProcessInstance processInstance : processInstances) {

            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            if (tasks.size() > 0) {
                myinitp.setWfTaskid(tasks.get(0).getId());
                WfMyinitiprocess wfMyinitiprocess1 = wfMyinitiprocessMapper.selectWfMyinitiprocessByTaskid(tasks.get(0).getId());
                if (wfMyinitiprocess1 == null) {
                    myinitp.setWfWfname(processInstance.getProcessDefinitionName());
                    myinitp.setWfStarttime(processInstance.getStartTime());
                    myinitp.setWfBusinesskey(processInstance.getBusinessKey());
                    myinitp.setWfState(processInstance.isSuspended() ? 1 : 0);
                    myinitp.setWfInitor(userId);
                    wfMyinitiprocessMapper.insertWfMyinitiprocess(myinitp);
                }
            }
        }
    }

    /**
     * 对我的待办操作
     */

    public void ProMyTodoProcess(String userId, String username, String approverid) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(approverid).list();

        for (Task task : tasks) {
            if (wfMytodoprocessMapper.selectWfMytodoprocessByTaskid(task.getId()) == null) {
                WfMytodoprocess builder = new WfMytodoprocess();
                builder.setWfTaskid(task.getId())
                        .setWfStarttime(task.getCreateTime())
                        .setWfBussiskey(task.getTaskDefinitionKey())
                        .setWfInitatorId(Long.parseLong(userId))
                        .setWfInitatorName(username)
                        .setWfFwname(task.getName())
                        .setWfApproverId(approverid);
                wfMytodoprocessMapper.insertWfMytodoprocess(builder);
            }
        }
    }

    public void passProcessUpdate(String taskId) {
        updateProcess(taskId, APPROVED.getValue(), null, PENDING.getValue());
    }

    public void rejectProcessUpdate(RejectVo rejectVo) {
        updateProcess(rejectVo.getTaskId(), REJECTED.getValue(), rejectVo.getReason().toString(), APPROVED.getValue());
    }

    private void updateProcess(String taskId, int initiprocessState, String rejectReason, int todoprocessState) {
        WfMyinitiprocess wfMyinitiprocess = new WfMyinitiprocess();
        wfMyinitiprocess.setWfTaskid(taskId);
        wfMyinitiprocess.setWfState(initiprocessState);
        if (rejectReason != null) {
            wfMyinitiprocess.setWfRejectrs(rejectReason);
        }
        wfMyinitiprocess.setWfApprtime(new Date());
        wfMyinitiprocessMapper.updateWfMyinitiprocess(wfMyinitiprocess);
        WfMytodoprocess wfMytodoprocess = new WfMytodoprocess();
        wfMytodoprocess.setWfTaskid(taskId);
        wfMytodoprocess.setWfState(todoprocessState);
        wfMytodoprocessMapper.updateWfMytodoprocess(wfMytodoprocess);

    }

    /**
     * 从数据库层面来讲，处理脏数据流程方法
     * 1.删除运行时参数：act_ru_variable
     * 2.删除act_ru_identitylink
     * 3.删除act_ru_task
     * 4.删除act_ru_execution
     */
    /**
     *  流程流转信息数据补充 流程开始不用分割线，没有审批的流程节点也不用分割线，当审批后再分割线，分割线中显示通过还是驳回，包括审批时间，审批人，
     */
    @GetMapping("flowInstanceInfo/{taskId}")
    public AjaxResult getFlowInstanceInfo(@PathVariable("taskId") String taskId) {
        List<FlowInstanceVO> flowInstanceVOList = new ArrayList<>();

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = "";
        if (task == null) {
            // 如果当前任务不存在，尝试从历史任务中获取processInstanceId
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            if (historicTaskInstance == null) {
                return success("任务不存在");
            }
            processInstanceId = historicTaskInstance.getProcessInstanceId();
        } else {
            // 获取任务对应的processInstanceId
            processInstanceId = task.getProcessInstanceId();
        }

        Set<String> actType= new HashSet<>();
        actType.add("startEvent");
        actType.add("endEvent");
        // 获取历史活动实例（开始事件和结束事件）
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityTypes(actType)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowInstanceVO flowInstanceVO = new FlowInstanceVO();
            flowInstanceVO.setTaskName(historicActivityInstance.getActivityName());
            flowInstanceVO.setActivityType(historicActivityInstance.getActivityType());
            flowInstanceVO.setStartTime(historicActivityInstance.getStartTime());
            flowInstanceVO.setEndTime(historicActivityInstance.getEndTime());

            // 计算耗时
            if (historicActivityInstance.getEndTime() != null && historicActivityInstance.getStartTime() != null) {
                long durationInMillis = historicActivityInstance.getEndTime().getTime() - historicActivityInstance.getStartTime().getTime();
                flowInstanceVO.setDuration(durationInMillis);
            }
            flowInstanceVOList.add(flowInstanceVO);
        }



        // 获取历史任务实例
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {

            FlowInstanceVO flowInstanceVO = new FlowInstanceVO();
            flowInstanceVO.setTaskName(historicTaskInstance.getName());
            flowInstanceVO.setActivityType("userTask");
            flowInstanceVO.setAssignee(historicTaskInstance.getAssignee());
            flowInstanceVO.setStartTime(historicTaskInstance.getStartTime());
            flowInstanceVO.setEndTime(historicTaskInstance.getEndTime());

            // 计算耗时
            if (historicTaskInstance.getEndTime() != null && historicTaskInstance.getStartTime() != null) {
                long durationInMillis = historicTaskInstance.getEndTime().getTime() - historicTaskInstance.getStartTime().getTime();
                flowInstanceVO.setDuration(durationInMillis);
            }
            if(null!=historicTaskInstance.getEndTime()){

                HistoricVariableInstance checkResult =null;
                // 获取历史变量实例以确定审批结果
                if(null!=historyService
                        .createHistoricVariableInstanceQuery()
                        .processInstanceId(historicTaskInstance.getProcessInstanceId())
                        .variableName("checkResult")
                        .singleResult()){
                    checkResult=historyService
                            .createHistoricVariableInstanceQuery()
                            .processInstanceId(historicTaskInstance.getProcessInstanceId())
                            .variableName("checkResult")
                            .singleResult();
                    CommentVO commentVO = new CommentVO();
                    commentVO.setType(checkResult.getValue().toString());
                    commentVO.setTime(historicTaskInstance.getEndTime().toString());
                    flowInstanceVO.setCommentList(Arrays.asList(commentVO));
                }else{//已经通过的，直接
                CommentVO commentVO = new CommentVO();
                commentVO.setType("通过");
                commentVO.setTime(historicTaskInstance.getStartTime().toString());
                flowInstanceVO.setCommentList(Arrays.asList(commentVO));
                }
            }

//            HistoricVariableInstance checkResultVar = historyService.createHistoricVariableInstanceQuery()
//                    .taskId(historicTaskInstance.getId())
//                    .variableName("checkResult")
//                    .singleResult();
//
//            if(checkResultVar != null){
//                CommentVO commentVO = new CommentVO();
//                commentVO.setType(checkResultVar.getValue().toString());
//                flowInstanceVO.setCommentList(commentVO);
//            }

            flowInstanceVOList.add(flowInstanceVO);
        }
        //未审批的流程，显示为待办
//        List<Task> currentTasks = taskService.createTaskQuery()
//                .processInstanceId(processInstanceId)
//                .list();
//        for(Task currentTask:currentTasks){
//            FlowInstanceVO flowInstanceVO = new FlowInstanceVO();
//            flowInstanceVO.setTaskName(currentTask.getName());
//            flowInstanceVO.setActivityType("userTask");
//            flowInstanceVO.setAssignee(currentTask.getAssignee());
//            flowInstanceVO.setStartTime(currentTask.getCreateTime());
//            flowInstanceVOList.add(flowInstanceVO);
//        }
        List<FlowInstanceVO> collect = flowInstanceVOList.stream()
                .sorted(Comparator.comparing(FlowInstanceVO::getStartTime).reversed()
                .thenComparing((vo -> "开始".equals(vo.getTaskName()) ? 1 : 0)))
                .collect(Collectors.toList());
        return success("查询成功",collect);
    }


//    public AjaxResult getFlowInstanceInfo(@PathVariable("taskId") String taskId) {
//        // 根据taskId查询任务
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        String processInstanceId = "";
//        if (task == null) {
//            // 如果当前任务不存在，尝试从历史任务中获取processInstanceId
//            HistoricTaskInstance historicTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//            if (historicTask == null) {
//                return success("任务不存在");
//            }
//            processInstanceId = historicTask.getProcessInstanceId();
//        } else {
//            // 获取任务对应的processInstanceId
//            processInstanceId = task.getProcessInstanceId();
//        }
//
//        // 获取历史流程实例信息
//        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//
//        if (historicProcessInstance == null) {
//            return success("流程实例不存在");
//        }
//
//        // 获取已完成的历史任务
//        List<HistoricTaskInstance> completedTasks = historyService.createHistoricTaskInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .finished()
//                .orderByHistoricTaskInstanceEndTime()
//                .asc()
//                .list();
//
//        // 构建流程流转信息
//        List<FlowInstanceVO> flowInstanceVOList = new ArrayList<>();
//
//        // 如果需要包含当前任务，可以在这里查询当前任务
//        // 但由于流程已经结束，当前任务列表应该为空
//        List<Task> currentTasks = taskService.createTaskQuery()
//                .processInstanceId(processInstanceId)
//                .list();
//        List<FlowInstanceVO> currtFlowable = currentTasks.stream()
//                .map(currentTask -> new FlowInstanceVO(
//                        currentTask.getId(),
//                        currentTask.getName(),
//                        currentTask.getAssignee(),
//                        currentTask.getCreateTime(),
//                        null,
//                        null,
//                        "el-icon-time",
//                        "#909399",
//                        "normal"
//                )).collect(Collectors.toList());
//        flowInstanceVOList.addAll(currtFlowable);
//
//
//        List<FlowInstanceVO> historyFlowable = completedTasks.stream()
//                .map(completedTask -> new FlowInstanceVO(
//                        completedTask.getId(),
//                        completedTask.getName(),
//                        completedTask.getAssignee(),
//                        completedTask.getStartTime(),
//                        completedTask.getEndTime(),
//                        TimeConvertUtil.format(completedTask.getDurationInMillis()),
//                        "el-icon-circle-check",
//                        "#008000"
//                        ,"normal"
//                ))
//                .collect(Collectors.toList());
//        flowInstanceVOList.addAll(historyFlowable);
//
//        FlowInstanceVO start = new FlowInstanceVO();
//        start.setTaskName("流程开始");
//        start.setAssignee(historicProcessInstance.getStartUserId());
//        start.setIcon("el-icon-circle-check");
//        start.setColor("#008000");
//        start.setStartTime(historicProcessInstance.getStartTime());
//        flowInstanceVOList.add(start);
//        return success("获取成功", flowInstanceVOList);
//    }


    @GetMapping("genProcessDiagramTest/{taskId}")
    public AjaxResult genProcessDiagramTest(HttpServletResponse httpServletResponse, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        // 从任务ID获取任务对象

        if (task == null) {
            log.error("任务ID {} 对应的任务不存在", taskId);
        }

        // 从任务对象中获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        if (processInstanceId == null) {
            log.error("任务ID {} 对应的流程实例ID为空", taskId);
            return null;
        }

        // 使用流程实例ID查询流程实例
        if (pi == null) {
            log.error("流程实例ID {} 对应的流程实例不存在或已结束", processInstanceId);
            return null;
        }

        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();
        //得到正在执行的Activity的Idm
        List<String> activityIds = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        BpmnXMLConverter converter = new BpmnXMLConverter();
        byte[] bpmnBytes = converter.convertToXML(bpmnModel);
        String bpmnXml = new String(bpmnBytes, StandardCharsets.UTF_8);

        Map<String, String> response = new HashMap<>();
        response.put("bpmn20Xml", bpmnXml);
        return success(response);
    }




}

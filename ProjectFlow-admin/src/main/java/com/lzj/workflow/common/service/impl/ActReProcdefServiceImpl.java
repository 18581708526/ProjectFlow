package com.lzj.workflow.common.service.impl;

import java.util.List;

import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.common.domain.ActReProcdef;
import com.lzj.workflow.common.mapper.ActReProcdefMapper;
import com.lzj.workflow.common.service.IActReProcdefService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lzj.common.core.domain.AjaxResult.error;
import static com.lzj.common.core.domain.AjaxResult.success;

/**
 * 流程定义Service业务层处理
 *
 * @author lzj
 * @date 2024-11-09
 */
@Service
public class ActReProcdefServiceImpl implements IActReProcdefService
{
    @Autowired
    private ActReProcdefMapper actReProcdefMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 查询流程定义
     *
     * @param id 流程定义主键
     * @return 流程定义
     */
    @Override
    public ActReProcdef selectActReProcdefById(String id)
    {
        return actReProcdefMapper.selectActReProcdefById(id);
    }

    /**
     * 查询流程定义列表
     *
     * @param actReProcdef 流程定义
     * @return 流程定义
     */
    @Override
    public List<ActReProcdef> selectActReProcdefList(ActReProcdef actReProcdef)
    {
        return actReProcdefMapper.selectActReProcdefList(actReProcdef);
    }

    /**
     * 新增流程定义
     *
     * @param actReProcdef 流程定义
     * @return 结果
     */
    @Override
    public int insertActReProcdef(ActReProcdef actReProcdef)
    {
        return actReProcdefMapper.insertActReProcdef(actReProcdef);
    }

    /**
     * 修改流程定义
     *
     * @param actReProcdef 流程定义
     * @return 结果
     */
    @Override
    public int updateActReProcdef(ActReProcdef actReProcdef)
    {
        return actReProcdefMapper.updateActReProcdef(actReProcdef);
    }

    /**
     * 批量删除流程定义
     *
     * @param ids 需要删除的流程定义主键
     * @return 结果
     */
    @Override
    public AjaxResult deleteActReProcdefByIds(String[] ids)
    {
        for(String processDefinitionId:ids){
            String deploymentId = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult().getDeploymentId();
            String name = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult().getName();
            List<ProcessInstance> activeProcessInstances = runtimeService.createProcessInstanceQuery()
                    .deploymentId(deploymentId)
                    .list();
            if(!activeProcessInstances.isEmpty()){
                return error("流程定义名称为"+name+"下还有"+activeProcessInstances.size()+"条在途流程未归档！！！处理后方可删除该流程定义");
            }
            repositoryService.deleteDeployment(deploymentId, true);
        }
          return success("已删除该流程定义与流程实例！！");
    }
    /**
     * 删除流程定义信息
     * @param id 流程定义主键
     * @return 结果
     */
    @Override
    public int deleteActReProcdefById(String id)
    {
        return actReProcdefMapper.deleteActReProcdefById(id);
    }

    @Override
    public AjaxResult disableProcess(String processDefinitionId) {
        repositoryService.suspendProcessDefinitionById(processDefinitionId,true,null);
        return success("流程定义ID："+processDefinitionId+"禁用成功！！！");
    }

    @Override
    public AjaxResult ableProcess(String processDefinitionId) {
        repositoryService.activateProcessDefinitionById(processDefinitionId,true,null);
        return success("流程定义ID："+processDefinitionId+"激活成功！！！");
    }

}

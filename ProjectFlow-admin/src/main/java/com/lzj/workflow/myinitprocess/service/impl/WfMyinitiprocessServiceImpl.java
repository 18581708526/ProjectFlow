package com.lzj.workflow.myinitprocess.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lzj.common.utils.SecurityUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lzj.workflow.myinitprocess.mapper.WfMyinitiprocessMapper;
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;
import com.lzj.workflow.myinitprocess.service.IWfMyinitiprocessService;

/**
 * 我的发起Service业务层处理
 *
 * @author lzj
 * @date 2024-11-11
 */
@Service
public class WfMyinitiprocessServiceImpl implements IWfMyinitiprocessService
{
    @Autowired
    private WfMyinitiprocessMapper wfMyinitiprocessMapper;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 查询我的发起
     *
     * @param wfWfid 我的发起主键
     * @return 我的发起
     */
    @Override
    public WfMyinitiprocess selectWfMyinitiprocessByWfWfid(Long wfWfid)
    {
        return wfMyinitiprocessMapper.selectWfMyinitiprocessByWfWfid(wfWfid);
    }

    /**
     * 查询我的发起列表
     *
     * @param wfMyinitiprocess 我的发起
     * @return 我的发起
     */
    @Override
    public List<WfMyinitiprocess> selectWfMyinitiprocessList(WfMyinitiprocess wfMyinitiprocess)
    {
        String userId =SecurityUtils.getUserId().toString();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .startedBy(userId)
                .list();
        WfMyinitiprocess myinitp = new WfMyinitiprocess();
        for (ProcessInstance processInstance : processInstances) {

            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
            if (tasks.size() > 0) {
                myinitp.setWfTaskid(tasks.get(0).getId());
                WfMyinitiprocess wfMyinitiprocess1 = wfMyinitiprocessMapper.selectWfMyinitiprocessByTaskid(tasks.get(0).getId());
                if(wfMyinitiprocess1 == null){
                    myinitp.setWfWfname(processInstance.getProcessDefinitionName());
                    myinitp.setWfStarttime(processInstance.getStartTime());
                    myinitp.setWfBusinesskey(processInstance.getBusinessKey());
                    myinitp.setWfState(processInstance.isSuspended() ? 1 : 0);
                    wfMyinitiprocessMapper.insertWfMyinitiprocess(myinitp);
                }
            }
        }

        return wfMyinitiprocessMapper.selectWfMyinitiprocessList(wfMyinitiprocess);
    }

    /**
     * 新增我的发起
     *
     * @param wfMyinitiprocess 我的发起
     * @return 结果
     */
    @Override
    public int insertWfMyinitiprocess(WfMyinitiprocess wfMyinitiprocess)
    {
        return wfMyinitiprocessMapper.insertWfMyinitiprocess(wfMyinitiprocess);
    }

    /**
     * 修改我的发起
     *
     * @param wfMyinitiprocess 我的发起
     * @return 结果
     */
    @Override
    public int updateWfMyinitiprocess(WfMyinitiprocess wfMyinitiprocess)
    {
        return wfMyinitiprocessMapper.updateWfMyinitiprocess(wfMyinitiprocess);
    }

    /**
     * 批量删除我的发起
     *
     * @param wfWfids 需要删除的我的发起主键
     * @return 结果
     */
    @Override
    public int deleteWfMyinitiprocessByWfWfids(Long[] wfWfids)
    {
        return wfMyinitiprocessMapper.deleteWfMyinitiprocessByWfWfids(wfWfids);
    }

    /**
     * 删除我的发起信息
     *
     * @param wfWfid 我的发起主键
     * @return 结果
     */
    @Override
    public int deleteWfMyinitiprocessByWfWfid(Long wfWfid)
    {
        return wfMyinitiprocessMapper.deleteWfMyinitiprocessByWfWfid(wfWfid);
    }
}

package com.lzj.workflow.mytodoprocess.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.lzj.common.utils.SecurityUtils;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lzj.workflow.mytodoprocess.mapper.WfMytodoprocessMapper;
import com.lzj.workflow.mytodoprocess.domain.WfMytodoprocess;
import com.lzj.workflow.mytodoprocess.service.IWfMytodoprocessService;

/**
 * 我的待办Service业务层处理
 *
 * @author lzj
 * @date 2024-11-12
 */
@Service
public class WfMytodoprocessServiceImpl implements IWfMytodoprocessService
{
    @Autowired
    private WfMytodoprocessMapper wfMytodoprocessMapper;
    @Autowired
    private TaskService taskService;

    /**
     * 查询我的待办
     *
     * @param wfFwid 我的待办主键
     * @return 我的待办
     */
    @Override
    public WfMytodoprocess selectWfMytodoprocessByWfFwid(Long wfFwid)
    {
        return wfMytodoprocessMapper.selectWfMytodoprocessByWfFwid(wfFwid);
    }

    /**
     * 查询我的待办列表
     *
     * @param wfMytodoprocess 我的待办
     * @return 我的待办
     */
    @Override
    public List<WfMytodoprocess> selectWfMytodoprocessList(WfMytodoprocess wfMytodoprocess)
    {
        return wfMytodoprocessMapper.selectWfMytodoprocessList(wfMytodoprocess);
    }

    /**
     * 新增我的待办
     *
     * @param wfMytodoprocess 我的待办
     * @return 结果
     */
    @Override
    public int insertWfMytodoprocess(WfMytodoprocess wfMytodoprocess)
    {
        return wfMytodoprocessMapper.insertWfMytodoprocess(wfMytodoprocess);
    }

    /**
     * 修改我的待办
     *
     * @param wfMytodoprocess 我的待办
     * @return 结果
     */
    @Override
    public int updateWfMytodoprocess(WfMytodoprocess wfMytodoprocess)
    {
        return wfMytodoprocessMapper.updateWfMytodoprocess(wfMytodoprocess);
    }

    /**
     * 批量删除我的待办
     *
     * @param wfFwids 需要删除的我的待办主键
     * @return 结果
     */
    @Override
    public int deleteWfMytodoprocessByWfFwids(Long[] wfFwids)
    {
        return wfMytodoprocessMapper.deleteWfMytodoprocessByWfFwids(wfFwids);
    }

    /**
     * 删除我的待办信息
     *
     * @param wfFwid 我的待办主键
     * @return 结果
     */
    @Override
    public int deleteWfMytodoprocessByWfFwid(Long wfFwid)
    {
        return wfMytodoprocessMapper.deleteWfMytodoprocessByWfFwid(wfFwid);
    }
}

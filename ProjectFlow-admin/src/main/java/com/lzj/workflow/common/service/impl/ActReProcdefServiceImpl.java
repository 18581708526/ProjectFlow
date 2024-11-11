package com.lzj.workflow.common.service.impl;

import java.util.List;

import com.lzj.workflow.common.domain.ActReProcdef;
import com.lzj.workflow.common.mapper.ActReProcdefMapper;
import com.lzj.workflow.common.service.IActReProcdefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int deleteActReProcdefByIds(String[] ids)
    {
        return actReProcdefMapper.deleteActReProcdefByIds(ids);
    }

    /**
     * 删除流程定义信息
     *
     * @param id 流程定义主键
     * @return 结果
     */
    @Override
    public int deleteActReProcdefById(String id)
    {
        return actReProcdefMapper.deleteActReProcdefById(id);
    }
}

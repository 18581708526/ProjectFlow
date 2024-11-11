package com.lzj.workflow.mapper;

import java.util.List;
import com.lzj.workflow.domain.ActReProcdef;

/**
 * 流程定义Mapper接口
 * 
 * @author lzj
 * @date 2024-11-09
 */
public interface ActReProcdefMapper 
{
    /**
     * 查询流程定义
     * 
     * @param id 流程定义主键
     * @return 流程定义
     */
    public ActReProcdef selectActReProcdefById(String id);

    /**
     * 查询流程定义列表
     * 
     * @param actReProcdef 流程定义
     * @return 流程定义集合
     */
    public List<ActReProcdef> selectActReProcdefList(ActReProcdef actReProcdef);

    /**
     * 新增流程定义
     * 
     * @param actReProcdef 流程定义
     * @return 结果
     */
    public int insertActReProcdef(ActReProcdef actReProcdef);

    /**
     * 修改流程定义
     * 
     * @param actReProcdef 流程定义
     * @return 结果
     */
    public int updateActReProcdef(ActReProcdef actReProcdef);

    /**
     * 删除流程定义
     * 
     * @param id 流程定义主键
     * @return 结果
     */
    public int deleteActReProcdefById(String id);

    /**
     * 批量删除流程定义
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActReProcdefByIds(String[] ids);
}

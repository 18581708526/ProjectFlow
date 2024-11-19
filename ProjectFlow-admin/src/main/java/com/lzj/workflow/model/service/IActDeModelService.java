package com.lzj.workflow.model.service;

import java.util.List;

import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.model.dto.UpdModelDto;
import com.lzj.workflow.model.domain.ActDeModel;


/**
 * 模型管理Service接口
 *
 * @author lzj
 * @date 2024-11-25
 */
public interface IActDeModelService
{
    /**
     * 查询模型管理
     *
     * @param id 模型管理主键
     * @return 模型管理
     */
    public ActDeModel selectActDeModelById(String id);

    /**
     * 查询模型管理列表
     *
     * @param actDeModel 模型管理
     * @return 模型管理集合
     */
    public List<ActDeModel> selectActDeModelList(ActDeModel actDeModel);

    /**
     * 新增模型管理
     *
     * @param actDeModel 模型管理
     * @return 结果
     */
    public String insertActDeModel(ActDeModel actDeModel);

    /**
     * 修改模型管理
     *
     * @param actDeModel 模型管理
     * @return 结果
     */
    public int updateActDeModel(ActDeModel actDeModel);

    /**
     * 批量删除模型管理
     *
     * @param ids 需要删除的模型管理主键集合
     * @return 结果
     */
    public int deleteActDeModelByIds(String[] ids);

    /**
     * 删除模型管理信息
     *
     * @param id 模型管理主键
     * @return 结果
     */
    public int deleteActDeModelById(String id);
    public AjaxResult updateModel(UpdModelDto updModelDto);

}

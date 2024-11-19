package com.lzj.workflow.model.mapper;

import java.util.List;
import com.lzj.workflow.model.domain.ActDeModel;
import org.apache.ibatis.annotations.Select;

/**
 * 模型管理Mapper接口
 *
 * @author lzj
 * @date 2024-11-25
 */
public interface ActDeModelMapper
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
    public int insertActDeModel(ActDeModel actDeModel);

    /**
     * 修改模型管理
     *
     * @param actDeModel 模型管理
     * @return 结果
     */
    public int updateActDeModel(ActDeModel actDeModel);

    /**
     * 删除模型管理
     *
     * @param id 模型管理主键
     * @return 结果
     */
    public int deleteActDeModelById(String id);

    /**
     * 批量删除模型管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteActDeModelByIds(String[] ids);

}

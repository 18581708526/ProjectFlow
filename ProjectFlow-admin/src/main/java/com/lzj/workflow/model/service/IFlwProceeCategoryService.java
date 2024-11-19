package com.lzj.workflow.model.service;

import java.util.List;

import com.lzj.common.core.domain.TreeSelect;
import com.lzj.workflow.model.domain.FlwProceeCategory;

/**
 * 类别管理Service接口
 *
 * @author lzj
 * @date 2024-12-15
 */
public interface IFlwProceeCategoryService
{
    /**
     * 查询类别管理
     *
     * @param id 类别管理主键
     * @return 类别管理
     */
    public FlwProceeCategory selectFlwProceeCategoryById(Long id);

    /**
     * 查询类别管理列表
     *
     * @param flwProceeCategory 类别管理
     * @return 类别管理集合
     */
    public List<FlwProceeCategory> selectFlwProceeCategoryList(FlwProceeCategory flwProceeCategory);

    /**
     * 新增类别管理
     *
     * @param flwProceeCategory 类别管理
     * @return 结果
     */
    public int insertFlwProceeCategory(FlwProceeCategory flwProceeCategory);

    /**
     * 修改类别管理
     *
     * @param flwProceeCategory 类别管理
     * @return 结果
     */
    public int updateFlwProceeCategory(FlwProceeCategory flwProceeCategory);

    /**
     * 批量删除类别管理
     *
     * @param ids 需要删除的类别管理主键集合
     * @return 结果
     */
    public int deleteFlwProceeCategoryByIds(Long[] ids);

    /**
     * 删除类别管理信息
     *
     * @param id 类别管理主键
     * @return 结果
     */
    public int deleteFlwProceeCategoryById(Long id);

    List<TreeSelect>  getFlowCategoryTree();
    public String getPartIdsForCategoryid(String id);
}

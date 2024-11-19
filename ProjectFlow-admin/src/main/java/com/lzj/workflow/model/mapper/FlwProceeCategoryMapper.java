package com.lzj.workflow.model.mapper;

import java.util.List;
import com.lzj.workflow.model.domain.FlwProceeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 类别管理Mapper接口
 *
 * @author lzj
 * @date 2024-12-15
 */
public interface FlwProceeCategoryMapper
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
     * 删除类别管理
     *
     * @param id 类别管理主键
     * @return 结果
     */
    public int deleteFlwProceeCategoryById(Long id);

    /**
     * 批量删除类别管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFlwProceeCategoryByIds(Long[] ids);
    @Select("select message from flw_procee_category where id=#{parentid}")
    public String getMessageByParentid(Long parentid);


}

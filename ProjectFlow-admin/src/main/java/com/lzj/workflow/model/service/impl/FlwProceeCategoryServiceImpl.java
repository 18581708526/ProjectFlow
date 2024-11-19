package com.lzj.workflow.model.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lzj.common.core.domain.TreeSelect;
import com.lzj.common.utils.DateUtils;
import com.lzj.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lzj.workflow.model.mapper.FlwProceeCategoryMapper;
import com.lzj.workflow.model.domain.FlwProceeCategory;
import com.lzj.workflow.model.service.IFlwProceeCategoryService;

/**
 * 类别管理Service业务层处理
 *
 * @author lzj
 * @date 2024-12-15
 */
@Service
public class FlwProceeCategoryServiceImpl implements IFlwProceeCategoryService {
    @Autowired
    private FlwProceeCategoryMapper flwProceeCategoryMapper;

    /**
     * 查询类别管理
     *
     * @param id 类别管理主键
     * @return 类别管理
     */
    @Override
    public FlwProceeCategory selectFlwProceeCategoryById(Long id) {
        return flwProceeCategoryMapper.selectFlwProceeCategoryById(id);
    }

    /**
     * 查询类别管理列表
     *
     * @param flwProceeCategory 类别管理
     * @return 类别管理
     */
    @Override
    public List<FlwProceeCategory> selectFlwProceeCategoryList(FlwProceeCategory flwProceeCategory) {
        return flwProceeCategoryMapper.selectFlwProceeCategoryList(flwProceeCategory);
    }

    /**
     * 新增类别管理
     *
     * @param flwProceeCategory 类别管理
     * @return 结果
     */
    @Override
    public int insertFlwProceeCategory(FlwProceeCategory flwProceeCategory) {
        flwProceeCategory.setCreateTime(DateUtils.getNowDate());
        flwProceeCategory.setCreateBy(SecurityUtils.getUsername());
        String messagePrefix = flwProceeCategoryMapper.getMessageByParentid(flwProceeCategory.getParentId());
        flwProceeCategory.setMessage(messagePrefix + "," + flwProceeCategory.getParentId().toString());
        return flwProceeCategoryMapper.insertFlwProceeCategory(flwProceeCategory);

    }

    /**
     * 修改类别管理
     *
     * @param flwProceeCategory 类别管理
     * @return 结果
     */
    @Override
    public int updateFlwProceeCategory(FlwProceeCategory flwProceeCategory) {
        flwProceeCategory.setUpdateTime(DateUtils.getNowDate());
        flwProceeCategory.setUpdateBy(SecurityUtils.getUsername());
        if (flwProceeCategory.getParentId() != 0) {
            String messagePrefix = flwProceeCategoryMapper.getMessageByParentid(flwProceeCategory.getParentId());
            flwProceeCategory.setMessage(messagePrefix + "," + flwProceeCategory.getParentId().toString());
        }
        return flwProceeCategoryMapper.updateFlwProceeCategory(flwProceeCategory);
    }

    /**
     * 批量删除类别管理
     *
     * @param ids 需要删除的类别管理主键
     * @return 结果
     */
    @Override
    public int deleteFlwProceeCategoryByIds(Long[] ids) {
        return flwProceeCategoryMapper.deleteFlwProceeCategoryByIds(ids);
    }

    /**
     * 删除类别管理信息
     *
     * @param id 类别管理主键
     * @return 结果
     */
    @Override
    public int deleteFlwProceeCategoryById(Long id) {
        return flwProceeCategoryMapper.deleteFlwProceeCategoryById(id);
    }

    @Override
    public  List<TreeSelect> getFlowCategoryTree() {
        List<FlwProceeCategory> flwProceeCategories = flwProceeCategoryMapper.selectFlwProceeCategoryList(null);
        //树形结构处理,使用stream流把list转化成map,key为分类id,value为节点分类对象本身
        Map<Long, FlwProceeCategory> collect = flwProceeCategories.stream()
                .collect(Collectors.toMap(FlwProceeCategory::getId, f -> f));
        List<FlwProceeCategory> list = new ArrayList<>();
        for(FlwProceeCategory flwProceeCategory:flwProceeCategories){
            //先判断是否为顶层父类，如果是顶层父类，直接添加到list中,getParentId()==0
            Long parentId = flwProceeCategory.getParentId();
            if(parentId==0){
                list.add(flwProceeCategory);
            }else{
                // else 如果是子类，并且能获取到父类，就把子类放在父类的chrilren属性中
                Long id = flwProceeCategory.getParentId();
                FlwProceeCategory proceeCategory = collect.get(id);
                if(proceeCategory!=null){
                    proceeCategory.getChildren().add(flwProceeCategory);
                }
            }
        }
        return convertToTreeSelect(list);
    }

    @Override
    public String getPartIdsForCategoryid(String id) {
        //通过自己查找所有的下级分类id
        List<FlwProceeCategory> flwProceeCategories= flwProceeCategoryMapper.selectFlwProceeCategoryList(null);
        Long parentId = flwProceeCategoryMapper.selectFlwProceeCategoryById(Long.valueOf(id)).getParentId();
        List<String> list ;
            if(parentId==0){//如果是顶层类型，直接返回所有下级分类id
                list = flwProceeCategories.stream().map(s -> s.getId().toString()).collect(Collectors.toList());
                return list.toString();
            }else{
                list = flwProceeCategories.stream()
                        .filter(s -> s.getMessage().contains("," + id + ",")
                                || s.getMessage().endsWith("," + id)
                                || s.getMessage().startsWith(id + ",") || s.getMessage().equals(id.toString()))
                        .map(s -> s.getId().toString())
                        .collect(Collectors.toList());
                list.add(id.toString());
                return list.toString();
            }
    }


    public List<TreeSelect> convertToTreeSelect(List<FlwProceeCategory> categories) {
        List<TreeSelect> treeSelectList = new ArrayList<>();
        for (FlwProceeCategory category : categories) {
            TreeSelect treeSelect = new TreeSelect(category.getId(), category.getName());
            treeSelect.setChildren(convertChildren(category.getChildren()));

            treeSelectList.add(treeSelect);
        }
        return treeSelectList;
    }

    private List<TreeSelect> convertChildren(List<FlwProceeCategory> children) {
        List<TreeSelect> childTreeSelects = new ArrayList<>();
        for (FlwProceeCategory child : children) {
            TreeSelect childTreeSelect = new TreeSelect(child.getId(), child.getName());
            childTreeSelect.setChildren(convertChildren(child.getChildren()));
            childTreeSelects.add(childTreeSelect);
        }
        return childTreeSelects;
    }
}

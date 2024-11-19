package com.lzj.workflow.model.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.lzj.common.core.domain.entity.SysDept;
import com.lzj.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lzj.common.annotation.Log;
import com.lzj.common.core.controller.BaseController;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.common.enums.BusinessType;
import com.lzj.workflow.model.domain.FlwProceeCategory;
import com.lzj.workflow.model.service.IFlwProceeCategoryService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 类别管理Controller
 *
 * @author lzj
 * @date 2024-12-15
 */
@RestController
@RequestMapping("/category/category")
public class FlwProceeCategoryController extends BaseController
{
    @Autowired
    private IFlwProceeCategoryService flwProceeCategoryService;

    /**
     * 查询类别管理列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlwProceeCategory flwProceeCategory)
    {
        startPage();
        List<FlwProceeCategory> list = flwProceeCategoryService.selectFlwProceeCategoryList(flwProceeCategory);
        return getDataTable(list);
    }

    /**
     * 导出类别管理列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:export')")
    @Log(title = "类别管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlwProceeCategory flwProceeCategory)
    {
        List<FlwProceeCategory> list = flwProceeCategoryService.selectFlwProceeCategoryList(flwProceeCategory);
        ExcelUtil<FlwProceeCategory> util = new ExcelUtil<FlwProceeCategory>(FlwProceeCategory.class);
        util.exportExcel(response, list, "类别管理数据");
    }
    /**
     * 获取类别管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('category:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(flwProceeCategoryService.selectFlwProceeCategoryById(id));
    }

    /**
     * 新增类别管理
     */
    @PreAuthorize("@ss.hasPermi('category:category:add')")
    @Log(title = "类别管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlwProceeCategory flwProceeCategory)
    {
        return toAjax(flwProceeCategoryService.insertFlwProceeCategory(flwProceeCategory));
    }

    /**
     * 修改类别管理
     */
    @PreAuthorize("@ss.hasPermi('category:category:edit')")
    @Log(title = "类别管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlwProceeCategory flwProceeCategory)
    {
        return toAjax(flwProceeCategoryService.updateFlwProceeCategory(flwProceeCategory));
    }

    /**
     * 删除类别管理
     */
    @PreAuthorize("@ss.hasPermi('category:category:remove')")
    @Log(title = "类别管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(flwProceeCategoryService.deleteFlwProceeCategoryByIds(ids));
    }
    /**
     * 修改流程分类去除子类
     * @param categoryid
     */
    @PreAuthorize("@ss.hasPermi('category:category:list')")
    @GetMapping("/exclude/{categoryid}")
    public AjaxResult getCategoryexculdeChild(@PathVariable(value = "categoryid") Long categoryid){
        List<FlwProceeCategory> categorys = flwProceeCategoryService.selectFlwProceeCategoryList(new FlwProceeCategory());
        categorys.removeIf(d -> d.getId().intValue() == categoryid || ArrayUtils.contains(StringUtils.split(d.getMessage(), ","), categoryid + ""));
        return success(categorys);
    }
    /**
     * 获取流程分类类别树
     */
    @GetMapping("/getcategoryTree")
    public AjaxResult getcategoryTree(){
        return success(flwProceeCategoryService.getFlowCategoryTree());
    }

}

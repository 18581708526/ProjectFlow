package com.lzj.workflow.mytodoprocess.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.lzj.common.utils.SecurityUtils;
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
import com.lzj.workflow.mytodoprocess.domain.WfMytodoprocess;
import com.lzj.workflow.mytodoprocess.service.IWfMytodoprocessService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 我的待办Controller
 *
 * @author lzj
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/mytodoprocess/mytodoprocess")
public class WfMytodoprocessController extends BaseController
{
    @Autowired
    private IWfMytodoprocessService wfMytodoprocessService;

    /**
     * 查询我的待办列表
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:list')")
    @GetMapping("/list")
    public TableDataInfo list(WfMytodoprocess wfMytodoprocess)
    {
        startPage();
        wfMytodoprocess.setWfApproverId(SecurityUtils.getUserId().toString());
        List<WfMytodoprocess> list = wfMytodoprocessService.selectWfMytodoprocessList(wfMytodoprocess);
        return getDataTable(list);
    }

    /**
     * 导出我的待办列表
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:export')")
    @Log(title = "我的待办", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WfMytodoprocess wfMytodoprocess)
    {
        List<WfMytodoprocess> list = wfMytodoprocessService.selectWfMytodoprocessList(wfMytodoprocess);
        ExcelUtil<WfMytodoprocess> util = new ExcelUtil<WfMytodoprocess>(WfMytodoprocess.class);
        util.exportExcel(response, list, "我的待办数据");
    }

    /**
     * 获取我的待办详细信息
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:query')")
    @GetMapping(value = "/{wfFwid}")
    public AjaxResult getInfo(@PathVariable("wfFwid") Long wfFwid)
    {
        return success(wfMytodoprocessService.selectWfMytodoprocessByWfFwid(wfFwid));
    }

    /**
     * 新增我的待办
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:add')")
    @Log(title = "我的待办", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfMytodoprocess wfMytodoprocess)
    {
        return toAjax(wfMytodoprocessService.insertWfMytodoprocess(wfMytodoprocess));
    }

    /**
     * 修改我的待办
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:edit')")
    @Log(title = "我的待办", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfMytodoprocess wfMytodoprocess)
    {
        return toAjax(wfMytodoprocessService.updateWfMytodoprocess(wfMytodoprocess));
    }

    /**
     * 删除我的待办
     */
    @PreAuthorize("@ss.hasPermi('mytodoprocess:mytodoprocess:remove')")
    @Log(title = "我的待办", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wfFwids}")
    public AjaxResult remove(@PathVariable Long[] wfFwids)
    {
        return toAjax(wfMytodoprocessService.deleteWfMytodoprocessByWfFwids(wfFwids));
    }
}

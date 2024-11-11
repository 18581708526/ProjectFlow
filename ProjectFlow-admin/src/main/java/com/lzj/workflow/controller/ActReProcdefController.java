package com.lzj.workflow.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.lzj.workflow.domain.ActReProcdef;
import com.lzj.workflow.service.IActReProcdefService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 流程定义Controller
 *
 * @author lzj
 * @date 2024-11-09
 */
@RestController
@RequestMapping("/workflow/workflow")
public class ActReProcdefController extends BaseController
{
    @Autowired
    private IActReProcdefService actReProcdefService;

    /**
     * 查询流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActReProcdef actReProcdef)
    {
        startPage();
        List<ActReProcdef> list = actReProcdefService.selectActReProcdefList(actReProcdef);
        return getDataTable(list);
    }

    /**
     * 导出流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:export')")
    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActReProcdef actReProcdef)
    {
        List<ActReProcdef> list = actReProcdefService.selectActReProcdefList(actReProcdef);
        ExcelUtil<ActReProcdef> util = new ExcelUtil<ActReProcdef>(ActReProcdef.class);
        util.exportExcel(response, list, "流程定义数据");
    }

    /**
     * 获取流程定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(actReProcdefService.selectActReProcdefById(id));
    }

    /**
     * 新增流程定义
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:add')")
    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActReProcdef actReProcdef)
    {
        return toAjax(actReProcdefService.insertActReProcdef(actReProcdef));
    }

    /**
     * 修改流程定义
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:edit')")
    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActReProcdef actReProcdef)
    {
        return toAjax(actReProcdefService.updateActReProcdef(actReProcdef));
    }

    /**
     * 删除流程定义
     */
    @PreAuthorize("@ss.hasPermi('workflow:workflow:remove')")
    @Log(title = "流程定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(actReProcdefService.deleteActReProcdefByIds(ids));
    }
}

package com.lzj.workflow.myinitprocess.controller;

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
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;
import com.lzj.workflow.myinitprocess.service.IWfMyinitiprocessService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 我的发起Controller
 *
 * @author lzj
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/myinitprocess/myinitiprocess")
public class WfMyinitiprocessController extends BaseController
{
    @Autowired
    private IWfMyinitiprocessService wfMyinitiprocessService;

    /**
     * 查询我的发起列表
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:list')")
    @GetMapping("/list")
    public TableDataInfo list(WfMyinitiprocess wfMyinitiprocess)
    {
        startPage();
        List<WfMyinitiprocess> list = wfMyinitiprocessService.selectWfMyinitiprocessList(wfMyinitiprocess);
        return getDataTable(list);
    }

    /**
     * 导出我的发起列表
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:export')")
    @Log(title = "我的发起", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WfMyinitiprocess wfMyinitiprocess)
    {
        List<WfMyinitiprocess> list = wfMyinitiprocessService.selectWfMyinitiprocessList(wfMyinitiprocess);
        ExcelUtil<WfMyinitiprocess> util = new ExcelUtil<WfMyinitiprocess>(WfMyinitiprocess.class);
        util.exportExcel(response, list, "我的发起数据");
    }

    /**
     * 获取我的发起详细信息
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:query')")
    @GetMapping(value = "/{wfWfid}")
    public AjaxResult getInfo(@PathVariable("wfWfid") Long wfWfid)
    {
        return success(wfMyinitiprocessService.selectWfMyinitiprocessByWfWfid(wfWfid));
    }

    /**
     * 新增我的发起
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:add')")
    @Log(title = "我的发起", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfMyinitiprocess wfMyinitiprocess)
    {
        return toAjax(wfMyinitiprocessService.insertWfMyinitiprocess(wfMyinitiprocess));
    }

    /**
     * 修改我的发起
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:edit')")
    @Log(title = "我的发起", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfMyinitiprocess wfMyinitiprocess)
    {
        return toAjax(wfMyinitiprocessService.updateWfMyinitiprocess(wfMyinitiprocess));
    }

    /**
     * 删除我的发起
     */
    @PreAuthorize("@ss.hasPermi('myinitprocess:myinitiprocess:remove')")
    @Log(title = "我的发起", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wfWfids}")
    public AjaxResult remove(@PathVariable Long[] wfWfids)
    {
        return toAjax(wfMyinitiprocessService.deleteWfMyinitiprocessByWfWfids(wfWfids));
    }
}

package com.lzj.execl.makenum.controller;

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
import com.lzj.execl.makenum.domain.BusExecl;
import com.lzj.execl.makenum.service.IBusExeclService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 凑数功能Controller
 *
 * @author lzj
 * @date 2024-12-03
 */
@RestController
@RequestMapping("/makenum/makenum")
public class BusExeclController extends BaseController
{
    @Autowired
    private IBusExeclService busExeclService;

    /**
     * 查询凑数功能列表
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusExecl busExecl)
    {
        startPage();
        List<BusExecl> list = busExeclService.selectBusExeclList(busExecl);
        return getDataTable(list);
    }

    /**
     * 导出凑数功能列表
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:export')")
    @Log(title = "凑数功能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusExecl busExecl)
    {
        List<BusExecl> list = busExeclService.selectBusExeclList(busExecl);
        ExcelUtil<BusExecl> util = new ExcelUtil<BusExecl>(BusExecl.class);
        util.exportExcel(response, list, "凑数功能数据");
    }

    /**
     * 获取凑数功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(busExeclService.selectBusExeclById(id));
    }

    /**
     * 新增凑数功能
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:add')")
    @Log(title = "凑数功能", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusExecl busExecl)
    {
        return toAjax(busExeclService.insertBusExecl(busExecl));
    }

    /**
     * 修改凑数功能
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:edit')")
    @Log(title = "凑数功能", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusExecl busExecl)
    {
        return toAjax(busExeclService.updateBusExecl(busExecl));
    }

    /**
     * 删除凑数功能
     */
    @PreAuthorize("@ss.hasPermi('makenum:makenum:remove')")
    @Log(title = "凑数功能", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busExeclService.deleteBusExeclByIds(ids));
    }
}

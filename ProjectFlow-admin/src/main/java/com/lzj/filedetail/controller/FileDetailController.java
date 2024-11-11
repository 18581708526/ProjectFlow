package com.lzj.filedetail.controller;

import com.lzj.common.annotation.Log;
import com.lzj.common.core.controller.BaseController;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.common.core.page.TableDataInfo;
import com.lzj.common.enums.BusinessType;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.filedetail.domain.FileDetail;
import com.lzj.filedetail.service.IFileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * filedetailController
 *
 * @author lzj
 * @date 2024-06-09
 */
@RestController
@RequestMapping("/filedetail/filedetail")
public class FileDetailController extends BaseController
{
    @Autowired
    private IFileDetailService fileDetailService;

    /**
     * 查询filedetail列表
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(FileDetail fileDetail)
    {
        startPage();
        List<FileDetail> list = fileDetailService.selectFileDetailList(fileDetail);
        return getDataTable(list);
    }

    /**
     * 导出filedetail列表
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:export')")
    @Log(title = "filedetail", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FileDetail fileDetail)
    {
        List<FileDetail> list = fileDetailService.selectFileDetailList(fileDetail);
        ExcelUtil<FileDetail> util = new ExcelUtil<FileDetail>(FileDetail.class);
        util.exportExcel(response, list, "filedetail数据");

    }

    /**
     * 获取filedetail详细信息
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(fileDetailService.selectFileDetailById(id));
    }

    /**
     * 新增filedetail
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:add')")
    @Log(title = "filedetail", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FileDetail fileDetail)
    {
        return toAjax(fileDetailService.insertFileDetail(fileDetail));
    }

    /**
     * 修改filedetail
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:edit')")
    @Log(title = "filedetail", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FileDetail fileDetail)
    {
        return toAjax(fileDetailService.updateFileDetail(fileDetail));
    }

    /**
     * 删除filedetail
     */
    @PreAuthorize("@ss.hasPermi('filedetail:filedetail:remove')")
    @Log(title = "filedetail", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fileDetailService.deleteFileDetailByIds(ids));
    }
}

package com.lzj.workflow.model.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import com.lzj.common.utils.SecurityUtils;
import com.lzj.workflow.model.dto.UpdModelDto;
import com.lzj.workflow.common.util.FlowableUiTokenUtil;
import com.lzj.workflow.model.mapper.FlwProceeCategoryMapper;
import com.lzj.workflow.model.service.IFlwProceeCategoryService;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.lzj.workflow.model.domain.ActDeModel;
import com.lzj.workflow.model.service.IActDeModelService;
import com.lzj.common.utils.poi.ExcelUtil;
import com.lzj.common.core.page.TableDataInfo;

/**
 * 模型管理Controller
 *
 * @author lzj
 * @date 2024-11-25
 */
@Slf4j
@RestController
@RequestMapping("/model/model")
public class ActDeModelController extends BaseController
{
    @Autowired
    private IActDeModelService actDeModelService;
    @Autowired
    private IFlwProceeCategoryService flwProceeCategoryService;



    /**
     * 查询模型管理列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActDeModel actDeModel)
    {
        startPage();
        //如果点击分类时需连下级分类一起展示
        if(actDeModel.getCategoryId()!=null){
            String ids = flwProceeCategoryService.getPartIdsForCategoryid(actDeModel.getCategoryId()).trim();
            // 将字符串转换为 List<Long>
            String modifiedString = ids.replaceAll("^\\[|\\]$", "");
            actDeModel.setCategoryId(modifiedString);
        }
        List<ActDeModel> list = actDeModelService.selectActDeModelList(actDeModel);

        return getDataTable(list);
    }

    /**
     * 导出模型管理列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:export')")
    @Log(title = "模型管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActDeModel actDeModel)
    {
        List<ActDeModel> list = actDeModelService.selectActDeModelList(actDeModel);
        ExcelUtil<ActDeModel> util = new ExcelUtil<ActDeModel>(ActDeModel.class);
        util.exportExcel(response, list, "模型管理数据");
    }

    /**
     * 获取模型管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(actDeModelService.selectActDeModelById(id));
    }

    /**
     * 新增模型管理
     */
    @PreAuthorize("@ss.hasPermi('model:model:add')")
    @Log(title = "模型管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActDeModel actDeModel)
    {
        String string = actDeModelService.insertActDeModel(actDeModel);
        return success("新增模型成功：模型id为"+string);
    }

    /**
     * 修改模型管理
     */
    @PreAuthorize("@ss.hasPermi('model:model:edit')")
    @Log(title = "模型管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActDeModel actDeModel)
    {
        return toAjax(actDeModelService.updateActDeModel(actDeModel));
    }

    /**
     * 删除模型管理
     */
    @PreAuthorize("@ss.hasPermi('model:model:remove')")
    @Log(title = "模型管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(actDeModelService.deleteActDeModelByIds(ids));
    }
    @GetMapping("/designProcess")
    public ResponseEntity designProcess()
    {
        HttpHeaders headers = FlowableUiTokenUtil.GetToken(SecurityUtils.getUsername());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    /**
     * 通过模型xml更新模型方法
     * @param updModelDto
     *
     *
     */
    @PreAuthorize("@ss.hasPermi('model:model:export')")
    @PostMapping("/updateModel")
    public AjaxResult updateModel(@RequestBody UpdModelDto updModelDto)
    {
     return actDeModelService.updateModel(updModelDto);
    }
}

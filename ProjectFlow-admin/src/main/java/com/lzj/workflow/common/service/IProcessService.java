package com.lzj.workflow.common.service;

import com.lzj.common.core.domain.AjaxResult;
import com.lzj.workflow.common.dto.ProcessModelDto;
import com.lzj.workflow.common.vo.ModelProVo;
public interface IProcessService {
    AjaxResult deployPro(String modelId);
    ProcessModelDto getModelResource(String modelId);
}

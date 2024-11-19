package com.lzj.workflow.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收前端传送的模型的一些参数
 * 用来对该模型进行部署流程的操作
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelProVo {
    private String modelId;
    private String key;
    private String name;
}

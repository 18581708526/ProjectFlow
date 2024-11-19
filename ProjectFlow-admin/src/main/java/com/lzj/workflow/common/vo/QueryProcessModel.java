package com.lzj.workflow.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据模型id获取模型key和模型名称的对象封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryProcessModel {
    private String modelKey;
    private String modelName;
}

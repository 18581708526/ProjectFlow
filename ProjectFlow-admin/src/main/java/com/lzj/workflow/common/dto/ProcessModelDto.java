package com.lzj.workflow.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessModelDto {
    private String modelId;
    private String modelName;
    private String modelKey;
    private String xml;
}

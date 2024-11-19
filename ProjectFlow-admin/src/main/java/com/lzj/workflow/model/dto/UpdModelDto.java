package com.lzj.workflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdModelDto {
    @Getter
    private BaseModelDto process;
    private String  svg;
    private String  xml;
    private String  modelId;
}

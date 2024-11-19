package com.lzj.workflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseModelDto {
    private String category = "";
    private String id;
    private String name;
}

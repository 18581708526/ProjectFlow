package com.lzj.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo {
    private String userId;
    private String name;
    private String DeptName;
}

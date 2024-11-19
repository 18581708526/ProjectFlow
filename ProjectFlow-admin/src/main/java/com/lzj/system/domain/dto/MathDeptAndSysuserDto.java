package com.lzj.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathDeptAndSysuserDto {
    //部门人员数量
    private int userNum;
    //部门id
    private String deptId;
    //部门名称
    private String deptName;
    //部门上级id串
    private String deptMassage;
}

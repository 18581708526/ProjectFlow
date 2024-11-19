package com.lzj.workflow.common.vo;

import lombok.*;

import java.lang.reflect.Field;

/**
 * 前端传来的请假流程对象封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QjProcessVo{
   private String  name; //学生姓名
   private String day; //请假天数
   private String  reason;//请假原因
   private String  key;
   private String  approver;

}

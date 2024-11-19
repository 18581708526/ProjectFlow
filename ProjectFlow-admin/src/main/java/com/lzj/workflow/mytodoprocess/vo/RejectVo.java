package com.lzj.workflow.mytodoprocess.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectVo {
    //任务id
    private String taskId;
    //驳回原因
    private Object reason;
}

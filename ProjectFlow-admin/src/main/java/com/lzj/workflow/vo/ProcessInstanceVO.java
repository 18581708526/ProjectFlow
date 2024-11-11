package com.lzj.workflow.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInstanceVO {
    private String id;
    private String processDefinitionId;
    private String businessKey;
    private String startUserId;
    private Date startTime;
}

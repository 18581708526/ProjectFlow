package com.lzj.workflow.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lzj.workflow.common.util.TimeConvertUtil;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 后端封装流程流转信息vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowInstanceVO {
//    private String taskId;
//    private String taskName;
//    private String assignee;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;
//    private String duration;
//    private String icon;
//
//    private String color;
//    private String size;

    private String taskName;
    private String activityType;
    private String assignee;
    private String candidate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String duration;
    private List<CommentVO> commentList;

    public void setDuration(long duration) {
        this.duration = TimeConvertUtil.format(duration) ;
    }
}

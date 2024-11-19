package com.lzj.workflow.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    private String type;
    private String time;
    private String fullMessage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if("通过".equals(type)){
            this.type = "1";
        }else if("驳回".equals(type)){
            this.type = "2";
        }else{
            this.type = type;
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }
}

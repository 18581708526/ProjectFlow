package com.lzj.workflow.common.vo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO{
        private String id;
        private String day;
        private String name;
        private QjProcessVo qjProcessVo;

        public TaskVO(String id, String day, String name) {
                this.id = id;
                this.day = day;
                this.name = name;
        }
}

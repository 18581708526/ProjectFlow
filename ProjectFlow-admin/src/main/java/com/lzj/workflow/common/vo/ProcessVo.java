package com.lzj.workflow.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 用来查询流程类型的封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessVo {
    //流程定义的唯一标识符
    private String key;
    //流程定义的名称
    private String name;
    //流程定义的版本号
    private int version;
    //流程定义所属的部署ID
    private String deploymentId;
    //流程定义资源的文件名
    private String resourceName;

    @Override
    public String toString() {
        return "流程详情信息:" +
                "流程ID='" + key + '\'' +
                ", 流程名称='" + name + '\'' +
                ", 版本号='" + version + '\'' +
                ", 流程部署ID='" + deploymentId + '\'' +
                ", 资源文件名='" + resourceName + '\'' +
                ' ';
    }

}

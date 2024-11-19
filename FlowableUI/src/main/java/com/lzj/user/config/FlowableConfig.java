package com.lzj.user.config;


import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {

    @Bean
    public BpmnJsonConverter bpmnJsonConverter() {
        return new BpmnJsonConverter();
    }
    @Bean
    public BpmnXMLConverter bpmnXMLConverter() {
        return new BpmnXMLConverter();
    }



}

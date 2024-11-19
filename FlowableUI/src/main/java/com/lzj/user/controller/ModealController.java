package com.lzj.user.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collector;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping(value ="/processmodel")
public class ModealController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private BpmnXMLConverter bpmnXmlConverter;
    @Autowired
    private BpmnJsonConverter bpmnJsonConverter;
    @PostMapping("/updateModel")
    public String addUserTest(String modelId,String bpmnXml,String svg){
        return updateModel(modelId,bpmnXml,svg);
    }
    public String updateModel(String modelId,String bpmnXml,String svg){
        Model model=null;
        //调用flowable原生方法去更新model模型数据
        try {
            XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
            InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(bpmnXml.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            //有xml文件，  需要转化成json和一个二进制的png
            BpmnModel bpmnModel = this.bpmnXmlConverter.convertToBpmnModel(xtr);
            Process process = bpmnModel.getProcesses().get(0);
            //更新act_de_model表信息

            if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                throw new BadRequestException("转化bpmnm模型失败！！");
            } else {
                if (bpmnModel.getLocationMap().size() == 0) {
                    BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                    bpmnLayout.execute();
                }
                ObjectNode modelNode = this.bpmnJsonConverter.convertToJson(bpmnModel);
                model= modelService.getModel(modelId);
                model.setModelEditorJson(modelNode.toString());
                model.setDescription(process.getDocumentation());
                model.setThumbnail(svg.getBytes());
                model.setKey(process.getId());
                model.setName(process.getName());
                //再去调用savemodel方法保存模型
                modelService.saveModel(model);
                return "模型名:"+model.getName()+" 更新成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "模型名:"+model.getName()+"更新失败";
        }
    }
}

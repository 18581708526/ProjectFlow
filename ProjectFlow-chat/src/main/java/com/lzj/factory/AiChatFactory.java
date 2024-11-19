package com.lzj.factory;

import com.lzj.constant.AiModel;
import com.lzj.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


//工厂模式，按照模型调用相应的聊天模型
@Component
public class AiChatFactory {
    private Map<String, AiService> selectModel=new HashMap<>();
    @Autowired
    public AiChatFactory(Map<String, AiService> selectModel){
        this.selectModel=selectModel;
    }
    public AiService getAiService(String type){
        return selectModel.getOrDefault(type,selectModel.get(AiModel.LOCAL));
    }
}

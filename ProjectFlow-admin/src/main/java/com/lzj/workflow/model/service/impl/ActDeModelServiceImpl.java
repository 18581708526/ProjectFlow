package com.lzj.workflow.model.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.lzj.common.constant.HttpStatus;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.common.utils.SecurityUtils;
import com.lzj.workflow.common.vo.ModelProVo;
import com.lzj.workflow.model.dto.UpdModelDto;
import com.lzj.workflow.common.exception.FlowableException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lzj.workflow.model.mapper.ActDeModelMapper;
import com.lzj.workflow.model.domain.ActDeModel;
import com.lzj.workflow.model.service.IActDeModelService;

import static com.lzj.common.core.domain.AjaxResult.error;
import static com.lzj.common.core.domain.AjaxResult.success;


/**
 * 模型管理Service业务层处理
 *
 * @author lzj
 * @date 2024-11-25
 */
@Slf4j
@Service
public class ActDeModelServiceImpl implements IActDeModelService
{
    @Autowired
    private ActDeModelMapper actDeModelMapper;

    /**
     * 查询模型管理
     *
     * @param id 模型管理主键
     * @return 模型管理
     */
    @Override
    public ActDeModel selectActDeModelById(String id)
    {
        return actDeModelMapper.selectActDeModelById(id);
    }

    /**
     * 查询模型管理列表
     *
     * @param actDeModel 模型管理
     * @return 模型管理
     */
    @Override
    public List<ActDeModel> selectActDeModelList(ActDeModel actDeModel) {return actDeModelMapper.selectActDeModelList(actDeModel);}

    /**
     * 新增模型管理
     *
     * @param actDeModel 模型管理
     * @return 结果
     */
    @Override
    public String insertActDeModel(ActDeModel actDeModel)
    {
        String string = UUID.randomUUID().toString();
        String modelName = actDeModel.getName();
        String modelKey = actDeModel.getModelKey();
        String modelDescription = actDeModel.getDescription();
        //插入操作
        actDeModel.setId(string);
        actDeModel.setModelType(0l);
        String Josn="{\"id\":\"canvas\",\"resourceId\":\"canvas\",\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\"}," +
                "\"properties\":{\"process_id\":\""+modelKey+"\",\"name\":\""+modelName+"\",\"documentation\":\""+modelDescription+"\"},\"childShapes\":" +
                "[{\"bounds\":{\"lowerRight\":{\"x\":130,\"y\":193},\"upperLeft\":{\"x\":100,\"y\":163}},\"childShapes\":[],\"dockers\":[],\"outgoing\":[]," +
                "\"resourceId\":\"startEvent1\",\"stencil\":{\"id\":\"StartNoneEvent\"}}]}";
        actDeModel.setModelEditorJson(Josn);
        actDeModel.setVersion(1l);
        actDeModel.setLastUpdatedBy(SecurityUtils.getUsername());
        actDeModel.setCreatedBy(SecurityUtils.getUsername());

        Timestamp currentTime = new Timestamp(new Date().getTime());
        actDeModel.setLastUpdated(currentTime);
        actDeModel.setCreated(currentTime);
        actDeModelMapper.insertActDeModel(actDeModel);
        return string;
    }

    /**
     * 修改模型管理
     *
     * @param actDeModel 模型管理
     * @return 结果
     */
    @Override
    public int updateActDeModel(ActDeModel actDeModel)
    {
        return actDeModelMapper.updateActDeModel(actDeModel);
    }

    /**
     * 批量删除模型管理
     *
     * @param ids 需要删除的模型管理主键
     * @return 结果
     */
    @Override
    public int deleteActDeModelByIds(String[] ids)
    {
        return actDeModelMapper.deleteActDeModelByIds(ids);
    }

    /**
     * 删除模型管理信息
     *
     * @param id 模型管理主键
     * @return 结果
     */
    @Override
    public int deleteActDeModelById(String id)
    {
        return actDeModelMapper.deleteActDeModelById(id);
    }

    @Override
    public AjaxResult updateModel(UpdModelDto updModelDto) {

        //通过modelke获取modelid
        String modelId = updModelDto.getModelId();
        // 创建 HttpClient 实例
        HttpClient client = HttpClient.newHttpClient();
        //&#39; 转成'号
        String bpmnXml=updModelDto.getXml().replace("&#39;","'");
        // 构建请求体
        String requestBody ="modelId="+modelId+
                "&bpmnXml="+bpmnXml+"&svg="+updModelDto.getSvg();
        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8888/processmodel/updateModel"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;

        try {
            // 发送请求并获取响应
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==HttpStatus.SUCCESS){
                // 输出响应状态码和响应体
                log.info("模型id:{}更新成功",modelId);
            }else{
                return error("操作失败",response.body());
            }

        } catch (Exception e) {
            throw new FlowableException(HttpStatus.ERROR,"连接flowbale接口失败");
        }
        return success("操作成功",response.body());
    }



}

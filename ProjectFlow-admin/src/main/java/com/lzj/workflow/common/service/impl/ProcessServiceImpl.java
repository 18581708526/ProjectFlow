package com.lzj.workflow.common.service.impl;

import com.google.common.base.Function;
import com.lzj.common.core.domain.AjaxResult;
import com.lzj.common.utils.SecurityUtils;
import com.lzj.workflow.common.dto.ProcessModelDto;
import com.lzj.workflow.common.exception.FlowableException;
import com.lzj.workflow.common.mapper.ActReProcdefMapper;
import com.lzj.workflow.common.service.IProcessService;
import com.lzj.workflow.common.util.FlowableUiTokenUtil;
import com.lzj.workflow.common.vo.ModelProVo;
import com.lzj.workflow.common.vo.QueryProcessModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.URL;

import static com.lzj.common.core.domain.AjaxResult.success;
import static com.lzj.workflow.common.util.FlowableUiTokenUtil.*;
@Slf4j
@Service
public class ProcessServiceImpl implements IProcessService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ActReProcdefMapper actReProcdefMapper;
    /**
     * 对bpmn.xml文件进行流程部署
     * @param modelId
     * @return
     */
    @Override
    public AjaxResult deployPro(String modelId) {
        return this.processPro(modelId);
    }
    /**
     * 获取文件xml
     * @param modelId
     * @return
     */
    @Override
    public ProcessModelDto getModelResource(String modelId) {
        InputStream inputStream = this.CommonGeResource(modelId);
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            log.error("读取文件内容时发生错误: {}", e.getMessage());
        }
        String xml = result.toString();
        ProcessModelDto processModelDto = new ProcessModelDto();
        processModelDto.setXml(xml);
        return processModelDto;
    }

    public AjaxResult processPro(String modelId) {
        // 实现无感登录后自动部署文件
        String deployId = "";
        InputStream inputStream = this.CommonGeResource(modelId);
        QueryProcessModel queryProcessModel = actReProcdefMapper.queryProcessModel(modelId);
        // 部署流程定义
        Deployment deploy = repositoryService.createDeployment()
                .addInputStream(queryProcessModel.getModelKey() + DEPLOYMENT_FILE_FIX, inputStream)
                .name(queryProcessModel.getModelName())
                .deploy();
        deployId = deploy.getId();
        log.info("流程定义已成功部署，流程实例id:{}", deployId);
        // 创建HTTP客户端

        return success("成功", "部署成功:流程定义id:" + deployId);
    }

    public InputStream CommonGeResource(String modelId) {
        HttpHeaders headers = FlowableUiTokenUtil.GetToken(SecurityUtils.getUsername());

        String url = DOWNLOAD_URL_STRAT + modelId + DOWNLOAD_URL_END;
        byte[] contentBytes = null;
        // 创建HTTP客户端
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(Cookie, headers.getFirst(SETCOOKIES));
            try (CloseableHttpResponse httpresponse = httpClient.execute(request)) {
                // 检查响应状态
                if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // 获取响应内容
                    HttpEntity entity = httpresponse.getEntity();
                    if (entity != null) {
                        contentBytes = EntityUtils.toByteArray(entity);
                    }
                } else {
                    log.error("无法下载文件，HTTP状态码 {}", httpresponse.getStatusLine().getStatusCode());
                }
            }
        } catch (Exception e) {
            throw new FlowableException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "获取文件失败，请稍后再试");
        }
        return contentBytes != null ? new ByteArrayInputStream(contentBytes) : null;
    }
}
//throw new FlowableException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "部署失败,请检查流程设计是否正确");

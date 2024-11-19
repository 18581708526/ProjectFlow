package com.lzj.workflow.model.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

/**
 * 模型管理对象 act_de_model
 *
 * @author lzj
 * @date 2024-11-25
 */
public class ActDeModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String name;

    /** 模型唯一Key */
    @Excel(name = "模型唯一Key")
    private String modelKey;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 提交信息 */
    @Excel(name = "提交信息")
    private String modelComment;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date created;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 修改人 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改人", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdated;

    /** 修改时间 */
    @Excel(name = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String lastUpdatedBy;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 描述json信息 */
    private String modelEditorJson;

    /** 预览图信息 */
    private String thumbnail;

    /** 流程类型 */
    @Excel(name = "流程类型")
    private Long modelType;

    /** 租户id */
    private String tenantId;

    private String categoryId;

    /** 租户id */
    private String categoryName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setModelKey(String modelKey)
    {
        this.modelKey = modelKey;
    }

    public String getModelKey()
    {
        return modelKey;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setModelComment(String modelComment)
    {
        this.modelComment = modelComment;
    }

    public String getModelComment()
    {
        return modelComment;
    }
    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getCreated()
    {
        return created;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setLastUpdated(Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated()
    {
        return lastUpdated;
    }
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setModelEditorJson(String modelEditorJson)
    {
        this.modelEditorJson = modelEditorJson;
    }

    public String getModelEditorJson()
    {
        return modelEditorJson;
    }
    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }
    public void setModelType(Long modelType)
    {
        this.modelType = modelType;
    }

    public Long getModelType()
    {
        return modelType;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("modelKey", getModelKey())
            .append("description", getDescription())
            .append("modelComment", getModelComment())
            .append("created", getCreated())
            .append("createdBy", getCreatedBy())
            .append("lastUpdated", getLastUpdated())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("version", getVersion())
            .append("modelEditorJson", getModelEditorJson())
            .append("thumbnail", getThumbnail())
            .append("modelType", getModelType())
            .append("tenantId", getTenantId())
            .append("category_id", getCategoryId())
            .append("category_name", getCategoryName())
            .toString();
    }
}

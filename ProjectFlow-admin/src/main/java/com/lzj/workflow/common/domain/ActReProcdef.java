package com.lzj.workflow.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

/**
 * 流程定义对象 act_re_procdef
 *
 * @author lzj
 * @date 2024-11-09
 */
public class ActReProcdef extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程定义的唯一标识符 */
    private String id;

    /** 记录的版本号，用于乐观锁机制 */
    @Excel(name = "记录的版本号，用于乐观锁机制")
    private Long rev;

    /** 流程定义的类别，通常用于分类管理 */
    @Excel(name = "流程定义的类别，通常用于分类管理")
    private String category;

    /** 流程定义的名称 */
    @Excel(name = "流程定义的名称")
    private String name;

    /** 流程定义的键，用于唯一标识一个流程模型 */
    @Excel(name = "流程定义的键，用于唯一标识一个流程模型")
    private String key;

    /** 流程定义的版本号，每次部署相同 KEY_ 的新流程定义时，版本号会递增 */
    @Excel(name = "流程定义的版本号，每次部署相同 KEY_ 的新流程定义时，版本号会递增")
    private Long version;

    /** 部署的唯一标识符，指向 act_re_deployment 表中的记录 */
    @Excel(name = "部署的唯一标识符，指向 act_re_deployment 表中的记录")
    private String deploymentId;

    /** 流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称 */
    @Excel(name = "流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称")
    private String resourceName;

    /** 流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称 */
    @Excel(name = "流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称")
    private String dgrmResourceName;

    /** 流程定义的描述信息 */
    @Excel(name = "流程定义的描述信息")
    private String description;

    /** 布尔值，表示该流程定义是否有一个启动表单 */
    @Excel(name = "布尔值，表示该流程定义是否有一个启动表单")
    private Integer hasStartFormKey;

    /**  布尔值，表示该流程定义是否包含图形表示 */
    @Excel(name = " 布尔值，表示该流程定义是否包含图形表示")
    private Integer hasGraphicalNotation;

    /** 指示流程定义的状态，1 表示激活状态，2 表示挂起状态。 */
    @Excel(name = "指示流程定义的状态，1 表示激活状态，2 表示挂起状态。")
    private Long suspensionState;

    /** 租户ID，用于多租户环境。 */
    @Excel(name = "租户ID，用于多租户环境。")
    private String tenantId;

    /** 引擎版本，表示创建流程定义时使用的Flowable引擎版本 */
    @Excel(name = "引擎版本，表示创建流程定义时使用的Flowable引擎版本")
    private String engineVersion;

    /** 派生自的流程定义ID，用于表示流程定义之间的继承关系 */
    @Excel(name = "派生自的流程定义ID，用于表示流程定义之间的继承关系")
    private String derivedFrom;

    /** 根流程定义ID，用于表示流程定义的根节点。 */
    @Excel(name = "根流程定义ID，用于表示流程定义的根节点。")
    private String derivedFromRoot;

    /** 派生版本号，用于表示从某个流程定义派生出的版本号。 */
    @Excel(name = "派生版本号，用于表示从某个流程定义派生出的版本号。")
    private Long derivedVersion;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setRev(Long rev)
    {
        this.rev = rev;
    }

    public Long getRev()
    {
        return rev;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setKey(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setDeploymentId(String deploymentId)
    {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }
    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }

    public String getResourceName()
    {
        return resourceName;
    }
    public void setDgrmResourceName(String dgrmResourceName)
    {
        this.dgrmResourceName = dgrmResourceName;
    }

    public String getDgrmResourceName()
    {
        return dgrmResourceName;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setHasStartFormKey(Integer hasStartFormKey)
    {
        this.hasStartFormKey = hasStartFormKey;
    }

    public Integer getHasStartFormKey()
    {
        return hasStartFormKey;
    }
    public void setHasGraphicalNotation(Integer hasGraphicalNotation)
    {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }

    public Integer getHasGraphicalNotation()
    {
        return hasGraphicalNotation;
    }
    public void setSuspensionState(Long suspensionState)
    {
        this.suspensionState = suspensionState;
    }

    public Long getSuspensionState()
    {
        return suspensionState;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }
    public void setEngineVersion(String engineVersion)
    {
        this.engineVersion = engineVersion;
    }

    public String getEngineVersion()
    {
        return engineVersion;
    }
    public void setDerivedFrom(String derivedFrom)
    {
        this.derivedFrom = derivedFrom;
    }

    public String getDerivedFrom()
    {
        return derivedFrom;
    }
    public void setDerivedFromRoot(String derivedFromRoot)
    {
        this.derivedFromRoot = derivedFromRoot;
    }

    public String getDerivedFromRoot()
    {
        return derivedFromRoot;
    }
    public void setDerivedVersion(Long derivedVersion)
    {
        this.derivedVersion = derivedVersion;
    }

    public Long getDerivedVersion()
    {
        return derivedVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rev", getRev())
            .append("category", getCategory())
            .append("name", getName())
            .append("key", getKey())
            .append("version", getVersion())
            .append("deploymentId", getDeploymentId())
            .append("resourceName", getResourceName())
            .append("dgrmResourceName", getDgrmResourceName())
            .append("description", getDescription())
            .append("hasStartFormKey", getHasStartFormKey())
            .append("hasGraphicalNotation", getHasGraphicalNotation())
            .append("suspensionState", getSuspensionState())
            .append("tenantId", getTenantId())
            .append("engineVersion", getEngineVersion())
            .append("derivedFrom", getDerivedFrom())
            .append("derivedFromRoot", getDerivedFromRoot())
            .append("derivedVersion", getDerivedVersion())
            .toString();
    }
}

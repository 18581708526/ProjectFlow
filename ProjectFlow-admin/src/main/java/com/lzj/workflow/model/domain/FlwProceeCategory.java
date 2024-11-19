package com.lzj.workflow.model.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类别管理对象 flw_procee_category
 *
 * @author lzj
 * @date 2024-12-15
 */
public class FlwProceeCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long parentId;

    /** 类别递归 */
    private String message;

    /** 类别名称 */
    @Excel(name = "类别名称")
    private String name;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Integer enable;

    /** 备注 */
    @Excel(name = "备注")
    private String commont;
    //方便查询分类树
    private List<FlwProceeCategory> children = new ArrayList<>();

    public List<FlwProceeCategory> getChildren() {
        return children;
    }

    public void setChildren(List<FlwProceeCategory> children) {
        this.children = children;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setEnable(Integer enable)
    {
        this.enable = enable;
    }

    public Integer getEnable()
    {
        return enable;
    }
    public void setCommont(String commont)
    {
        this.commont = commont;
    }

    public String getCommont()
    {
        return commont;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("message", getMessage())
            .append("name", getName())
            .append("enable", getEnable())
            .append("commont", getCommont())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("children", getChildren())
            .toString();
    }
}

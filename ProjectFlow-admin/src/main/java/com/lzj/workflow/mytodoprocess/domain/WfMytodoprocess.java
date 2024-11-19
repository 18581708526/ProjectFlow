package com.lzj.workflow.mytodoprocess.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

/**
 * 我的待办对象 wf_mytodoprocess
 *
 * @author lzj
 * @date 2024-11-12
 */
public class WfMytodoprocess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 我的待办流程主键id */
    private Long wfFwid;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String wfFwname;

    /** 发起人id，外键约束User_id */
    private Long wfInitatorId;

    /** 流程发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "流程发起时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date wfStarttime;

    /** 流程实例的taskid，用于确定流程任务唯一值 */
    private String wfTaskid;

    /** 流程业务键 */
    private String wfBussiskey;

    /** 发起人 */
    @Excel(name = "发起人")
    private String wfInitatorName;

    @Excel(name = "流程状态")
    private int wfState;
    @Excel(name = "流程审批人id")
    private String wfApproverId;

    public String getWfApproverId() {
        return wfApproverId;
    }

    public void setWfApproverId(String wfApproverId) {
        this.wfApproverId = wfApproverId;
    }

    public int getWfState() {
        return wfState;
    }

    public void setWfState(int wfState) {
        this.wfState = wfState;
    }

    public void setWfFwid(Long wfFwid)
    {
        this.wfFwid = wfFwid;
    }

    public Long getWfFwid()
    {
        return wfFwid;
    }
    public WfMytodoprocess setWfFwname(String wfFwname)
    {
        this.wfFwname = wfFwname;
        return this;
    }

    public String getWfFwname()
    {
        return wfFwname;
    }
    public WfMytodoprocess setWfInitatorId(Long wfInitatorId)
    {
        this.wfInitatorId = wfInitatorId;
        return this;
    }

    public Long getWfInitatorId()
    {
        return wfInitatorId;
    }
    public WfMytodoprocess setWfStarttime(Date wfStarttime)
    {
        this.wfStarttime = wfStarttime;
        return this;
    }

    public Date getWfStarttime()
    {
        return wfStarttime;
    }
    public WfMytodoprocess setWfTaskid(String wfTaskid)
    {
        this.wfTaskid = wfTaskid;
        return this;
    }

    public String getWfTaskid()
    {
        return wfTaskid;
    }
    public WfMytodoprocess setWfBussiskey(String wfBussiskey)
    {
        this.wfBussiskey = wfBussiskey;
        return this;
    }

    public String getWfBussiskey()
    {
        return wfBussiskey;
    }
    public WfMytodoprocess setWfInitatorName(String wfInitatorName)
    {
        this.wfInitatorName = wfInitatorName;
        return this;
    }

    public String getWfInitatorName()
    {
        return wfInitatorName;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wfFwid", getWfFwid())
            .append("wfFwname", getWfFwname())
            .append("wfInitatorId", getWfInitatorId())
            .append("wfStarttime", getWfStarttime())
            .append("wfTaskid", getWfTaskid())
            .append("wfBussiskey", getWfBussiskey())
            .append("wfInitatorName", getWfInitatorName())
            .toString();
    }
}

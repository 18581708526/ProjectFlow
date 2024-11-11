package com.lzj.workflow.myinitprocess.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

/**
 * 我的发起对象 wf_myinitiprocess
 *
 * @author lzj
 * @date 2024-11-11
 */
public class WfMyinitiprocess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 我的发起流程数据表主键id */
    private Long wfWfid;

    /** 流程实例taskid */
    private String wfTaskid;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String wfWfname;

    /** 流程业务键 */
    private String wfBusinesskey;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date wfStarttime;

    /** 流程状态：0审批中，1审批通过，2驳回 */
    @Excel(name = "流程状态：0审批中，1审批通过，2驳回")
    private Integer wfState;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date wfApprtime;

    public void setWfWfid(Long wfWfid)
    {
        this.wfWfid = wfWfid;
    }

    public Long getWfWfid()
    {
        return wfWfid;
    }
    public void setWfTaskid(String wfTaskid)
    {
        this.wfTaskid = wfTaskid;
    }

    public String getWfTaskid()
    {
        return wfTaskid;
    }
    public void setWfWfname(String wfWfname)
    {
        this.wfWfname = wfWfname;
    }

    public String getWfWfname()
    {
        return wfWfname;
    }
    public void setWfBusinesskey(String wfBusinesskey)
    {
        this.wfBusinesskey = wfBusinesskey;
    }

    public String getWfBusinesskey()
    {
        return wfBusinesskey;
    }
    public void setWfStarttime(Date wfStarttime)
    {
        this.wfStarttime = wfStarttime;
    }

    public Date getWfStarttime()
    {
        return wfStarttime;
    }
    public void setWfState(Integer wfState)
    {
        this.wfState = wfState;
    }

    public Integer getWfState()
    {
        return wfState;
    }
    public void setWfApprtime(Date wfApprtime)
    {
        this.wfApprtime = wfApprtime;
    }

    public Date getWfApprtime()
    {
        return wfApprtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wfWfid", getWfWfid())
            .append("wfTaskid", getWfTaskid())
            .append("wfWfname", getWfWfname())
            .append("wfBusinesskey", getWfBusinesskey())
            .append("wfStarttime", getWfStarttime())
            .append("wfState", getWfState())
            .append("wfApprtime", getWfApprtime())
            .toString();
    }
}

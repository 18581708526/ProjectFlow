package com.lzj.execl.makenum.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;

/**
 * 凑数功能对象 bus_execl
 *
 * @author lzj
 * @date 2024-12-03
 */
public class BusExecl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 操作名称 */
    @Excel(name = "操作名称")
    private String busName;

    /** 操作sheet页 */
    @Excel(name = "操作sheet页")
    private Long sheetNumber;

    /** 行名称 */
    @Excel(name = "行名称")
    private String rowName;

    /** 列名称 */
    @Excel(name = "列名称")
    private String colName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBusName(String busName)
    {
        this.busName = busName;
    }

    public String getBusName()
    {
        return busName;
    }
    public void setSheetNumber(Long sheetNumber)
    {
        this.sheetNumber = sheetNumber;
    }

    public Long getSheetNumber()
    {
        return sheetNumber;
    }
    public void setRowName(String rowName)
    {
        this.rowName = rowName;
    }

    public String getRowName()
    {
        return rowName;
    }
    public void setColName(String colName)
    {
        this.colName = colName;
    }

    public String getColName()
    {
        return colName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("busName", getBusName())
            .append("sheetNumber", getSheetNumber())
            .append("rowName", getRowName())
            .append("colName", getColName())
            .toString();
    }
}

package com.lzj.student.domain;

import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生信息对象 student
 *
 * @author ruoyi
 * @date 2024-06-09
 */
public class Student extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String stuName;

    /** 学生学号 */
    @Excel(name = "学生学号")
    private String stuCode;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 学生附件 */
    @Excel(name = "学生附件")
    private String stuFile;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStuName(String stuName)
    {
        this.stuName = stuName;
    }

    public String getStuName()
    {
        return stuName;
    }
    public void setStuCode(String stuCode)
    {
        this.stuCode = stuCode;
    }

    public String getStuCode()
    {
        return stuCode;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setStuFile(String stuFile)
    {
        this.stuFile = stuFile;
    }

    public String getStuFile()
    {
        return stuFile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stuName", getStuName())
            .append("stuCode", getStuCode())
            .append("sex", getSex())
            .append("stuFile", getStuFile())
            .toString();
    }
}

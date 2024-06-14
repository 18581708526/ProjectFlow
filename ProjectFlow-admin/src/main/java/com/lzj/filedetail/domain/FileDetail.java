package com.lzj.filedetail.domain;


import com.lzj.common.annotation.Excel;
import com.lzj.common.core.domain.BaseEntity;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * file detail对象 file_detail
 *
 * @author ruoyi
 * @date 2024-06-09
 */

@Getter
public class FileDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件预览 */
    @Excel(name = "文件预览")
    private String fileUrl;

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("fileUrl", getFileUrl())
            .toString();
    }
}

package com.lzj.filedetail.service;

import com.lzj.filedetail.domain.FileDetail;

import java.util.List;

/**
 * filedetailService接口
 *
 * @author ruoyi
 * @date 2024-06-09
 */
public interface IFileDetailService
{
    /**
     * 查询filedetail
     *
     * @param id filedetail主键
     * @return filedetail
     */
    public FileDetail selectFileDetailById(Long id);

    /**
     * 查询filedetail列表
     *
     * @param fileDetail filedetail
     * @return filedetail集合
     */
    public List<FileDetail> selectFileDetailList(FileDetail fileDetail);

    /**
     * 新增filedetail
     *
     * @param fileDetail filedetail
     * @return 结果
     */
    public int insertFileDetail(FileDetail fileDetail);

    /**
     * 修改filedetail
     *
     * @param fileDetail filedetail
     * @return 结果
     */
    public int updateFileDetail(FileDetail fileDetail);

    /**
     * 批量删除filedetail
     *
     * @param ids 需要删除的filedetail主键集合
     * @return 结果
     */
    public int deleteFileDetailByIds(Long[] ids);

    /**
     * 删除filedetail信息
     *
     * @param id filedetail主键
     * @return 结果
     */
    public int deleteFileDetailById(Long id);
}

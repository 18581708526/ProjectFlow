package com.lzj.filedetail.mapper;

import com.lzj.filedetail.domain.FileDetail;

import java.util.List;

/**
 * filedetailMapper接口
 *
 * @author ruoyi
 * @date 2024-06-09
 */
public interface FileDetailMapper
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
     * 删除filedetail
     *
     * @param id filedetail主键
     * @return 结果
     */
    public int deleteFileDetailById(Long id);

    /**
     * 批量删除filedetail
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileDetailByIds(Long[] ids);
}

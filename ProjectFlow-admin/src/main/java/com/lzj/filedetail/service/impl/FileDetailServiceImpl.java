package com.lzj.filedetail.service.impl;

import com.lzj.filedetail.domain.FileDetail;
import com.lzj.filedetail.mapper.FileDetailMapper;
import com.lzj.filedetail.service.IFileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * filedetailService业务层处理
 *
 * @author ruoyi
 * @date 2024-06-09
 */
@Service
public class FileDetailServiceImpl implements IFileDetailService
{
    @Autowired
    private FileDetailMapper fileDetailMapper;

    /**
     * 查询filedetail
     *
     * @param id filedetail主键
     * @return filedetail
     */
    @Override
    public FileDetail selectFileDetailById(Long id)
    {
        return fileDetailMapper.selectFileDetailById(id);
    }

    /**
     * 查询filedetail列表
     *
     * @param fileDetail filedetail
     * @return filedetail
     */
    @Override
    public List<FileDetail> selectFileDetailList(FileDetail fileDetail)
    {
        return fileDetailMapper.selectFileDetailList(fileDetail);
    }

    /**
     * 新增filedetail
     *
     * @param fileDetail filedetail
     * @return 结果
     */
    @Override
    public int insertFileDetail(FileDetail fileDetail)
    {
        return fileDetailMapper.insertFileDetail(fileDetail);
    }

    /**
     * 修改filedetail
     *
     * @param fileDetail filedetail
     * @return 结果
     */
    @Override
    public int updateFileDetail(FileDetail fileDetail)
    {
        return fileDetailMapper.updateFileDetail(fileDetail);
    }

    /**
     * 批量删除filedetail
     *
     * @param ids 需要删除的filedetail主键
     * @return 结果
     */
    @Override
    public int deleteFileDetailByIds(Long[] ids)
    {
        return fileDetailMapper.deleteFileDetailByIds(ids);
    }

    /**
     * 删除filedetail信息
     *
     * @param id filedetail主键
     * @return 结果
     */
    @Override
    public int deleteFileDetailById(Long id)
    {
        return fileDetailMapper.deleteFileDetailById(id);
    }
}

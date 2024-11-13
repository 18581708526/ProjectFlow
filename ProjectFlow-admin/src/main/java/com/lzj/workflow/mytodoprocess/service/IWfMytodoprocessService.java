package com.lzj.workflow.mytodoprocess.service;

import java.util.List;
import com.lzj.workflow.mytodoprocess.domain.WfMytodoprocess;

/**
 * 我的待办Service接口
 * 
 * @author lzj
 * @date 2024-11-12
 */
public interface IWfMytodoprocessService 
{
    /**
     * 查询我的待办
     * 
     * @param wfFwid 我的待办主键
     * @return 我的待办
     */
    public WfMytodoprocess selectWfMytodoprocessByWfFwid(Long wfFwid);

    /**
     * 查询我的待办列表
     * 
     * @param wfMytodoprocess 我的待办
     * @return 我的待办集合
     */
    public List<WfMytodoprocess> selectWfMytodoprocessList(WfMytodoprocess wfMytodoprocess);

    /**
     * 新增我的待办
     * 
     * @param wfMytodoprocess 我的待办
     * @return 结果
     */
    public int insertWfMytodoprocess(WfMytodoprocess wfMytodoprocess);

    /**
     * 修改我的待办
     * 
     * @param wfMytodoprocess 我的待办
     * @return 结果
     */
    public int updateWfMytodoprocess(WfMytodoprocess wfMytodoprocess);

    /**
     * 批量删除我的待办
     * 
     * @param wfFwids 需要删除的我的待办主键集合
     * @return 结果
     */
    public int deleteWfMytodoprocessByWfFwids(Long[] wfFwids);

    /**
     * 删除我的待办信息
     * 
     * @param wfFwid 我的待办主键
     * @return 结果
     */
    public int deleteWfMytodoprocessByWfFwid(Long wfFwid);
}

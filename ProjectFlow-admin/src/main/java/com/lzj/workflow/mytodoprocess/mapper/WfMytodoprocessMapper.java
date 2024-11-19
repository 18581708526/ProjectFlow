package com.lzj.workflow.mytodoprocess.mapper;

import java.util.List;
import com.lzj.workflow.mytodoprocess.domain.WfMytodoprocess;
import org.apache.ibatis.annotations.Param;

/**
 * 我的待办Mapper接口
 *
 * @author lzj
 * @date 2024-11-12
 */
public interface WfMytodoprocessMapper
{
    /**
     * 查询我的待办
     *
     * @param wfFwid 我的待办主键
     * @return 我的待办
     */
    public WfMytodoprocess selectWfMytodoprocessByWfFwid(Long wfFwid);
    public WfMytodoprocess selectWfMytodoprocessByTaskid(String taskid);
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
     * 删除我的待办
     *
     * @param wfFwid 我的待办主键
     * @return 结果
     */
    public int deleteWfMytodoprocessByWfFwid(Long wfFwid);

    /**
     * 批量删除我的待办
     *
     * @param wfFwids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfMytodoprocessByWfFwids(Long[] wfFwids);
}

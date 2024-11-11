package com.lzj.workflow.myinitprocess.mapper;

import java.util.List;
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;

/**
 * 我的发起Mapper接口
 *
 * @author lzj
 * @date 2024-11-11
 */
public interface WfMyinitiprocessMapper
{
    /**
     * 查询我的发起
     *
     * @param wfWfid 我的发起主键
     * @return 我的发起
     */
    public WfMyinitiprocess selectWfMyinitiprocessByWfWfid(Long wfWfid);
    public WfMyinitiprocess selectWfMyinitiprocessByTaskid(String Taskid);
    /**
     * 查询我的发起列表
     *
     * @param wfMyinitiprocess 我的发起
     * @return 我的发起集合
     */
    public List<WfMyinitiprocess> selectWfMyinitiprocessList(WfMyinitiprocess wfMyinitiprocess);

    /**
     * 新增我的发起
     *
     * @param wfMyinitiprocess 我的发起
     * @return 结果
     */
    public int insertWfMyinitiprocess(WfMyinitiprocess wfMyinitiprocess);

    /**
     * 修改我的发起
     *
     * @param wfMyinitiprocess 我的发起
     * @return 结果
     */
    public int updateWfMyinitiprocess(WfMyinitiprocess wfMyinitiprocess);

    /**
     * 删除我的发起
     *
     * @param wfWfid 我的发起主键
     * @return 结果
     */
    public int deleteWfMyinitiprocessByWfWfid(Long wfWfid);

    /**
     * 批量删除我的发起
     *
     * @param wfWfids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWfMyinitiprocessByWfWfids(Long[] wfWfids);
}

package com.lzj.workflow.myinitprocess.service;

import java.util.List;
import com.lzj.workflow.myinitprocess.domain.WfMyinitiprocess;

/**
 * 我的发起Service接口
 *
 * @author lzj
 * @date 2024-11-11
 */
public interface IWfMyinitiprocessService
{
    /**
     * 查询我的发起
     *
     * @param wfWfid 我的发起主键
     * @return 我的发起
     */
    public WfMyinitiprocess selectWfMyinitiprocessByWfWfid(Long wfWfid);

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
     * 批量删除我的发起
     *
     * @param wfWfids 需要删除的我的发起主键集合
     * @return 结果
     */
    public int deleteWfMyinitiprocessByWfWfids(Long[] wfWfids);

    /**
     * 删除我的发起信息
     *
     * @param wfWfid 我的发起主键
     * @return 结果
     */
    public int deleteWfMyinitiprocessByWfWfid(Long wfWfid);
    public String selectRejectRstoProcess(String wfTaskId);

}

package com.lzj.execl.makenum.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lzj.execl.makenum.mapper.BusExeclMapper;
import com.lzj.execl.makenum.domain.BusExecl;
import com.lzj.execl.makenum.service.IBusExeclService;

/**
 * 凑数功能Service业务层处理
 *
 * @author lzj
 * @date 2024-12-03
 */
@Service
public class BusExeclServiceImpl implements IBusExeclService
{
    @Autowired
    private BusExeclMapper busExeclMapper;

    /**
     * 查询凑数功能
     *
     * @param id 凑数功能主键
     * @return 凑数功能
     */
    @Override
    public BusExecl selectBusExeclById(Long id)
    {
        return busExeclMapper.selectBusExeclById(id);
    }

    /**
     * 查询凑数功能列表
     *
     * @param busExecl 凑数功能
     * @return 凑数功能
     */
    @Override
    public List<BusExecl> selectBusExeclList(BusExecl busExecl)
    {
        return busExeclMapper.selectBusExeclList(busExecl);
    }

    /**
     * 新增凑数功能
     *
     * @param busExecl 凑数功能
     * @return 结果
     */
    @Override
    public int insertBusExecl(BusExecl busExecl)
    {
        return busExeclMapper.insertBusExecl(busExecl);
    }

    /**
     * 修改凑数功能
     *
     * @param busExecl 凑数功能
     * @return 结果
     */
    @Override
    public int updateBusExecl(BusExecl busExecl)
    {
        return busExeclMapper.updateBusExecl(busExecl);
    }

    /**
     * 批量删除凑数功能
     *
     * @param ids 需要删除的凑数功能主键
     * @return 结果
     */
    @Override
    public int deleteBusExeclByIds(Long[] ids)
    {
        return busExeclMapper.deleteBusExeclByIds(ids);
    }

    /**
     * 删除凑数功能信息
     *
     * @param id 凑数功能主键
     * @return 结果
     */
    @Override
    public int deleteBusExeclById(Long id)
    {
        return busExeclMapper.deleteBusExeclById(id);
    }
}

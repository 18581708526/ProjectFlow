package com.lzj.execl.makenum.mapper;

import java.util.List;
import com.lzj.execl.makenum.domain.BusExecl;

/**
 * 凑数功能Mapper接口
 * 
 * @author lzj
 * @date 2024-12-03
 */
public interface BusExeclMapper 
{
    /**
     * 查询凑数功能
     * 
     * @param id 凑数功能主键
     * @return 凑数功能
     */
    public BusExecl selectBusExeclById(Long id);

    /**
     * 查询凑数功能列表
     * 
     * @param busExecl 凑数功能
     * @return 凑数功能集合
     */
    public List<BusExecl> selectBusExeclList(BusExecl busExecl);

    /**
     * 新增凑数功能
     * 
     * @param busExecl 凑数功能
     * @return 结果
     */
    public int insertBusExecl(BusExecl busExecl);

    /**
     * 修改凑数功能
     * 
     * @param busExecl 凑数功能
     * @return 结果
     */
    public int updateBusExecl(BusExecl busExecl);

    /**
     * 删除凑数功能
     * 
     * @param id 凑数功能主键
     * @return 结果
     */
    public int deleteBusExeclById(Long id);

    /**
     * 批量删除凑数功能
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBusExeclByIds(Long[] ids);
}

package com.lzj.system.mapper;

import com.lzj.system.domain.SysConfig;
import com.lzj.system.domain.dto.MathDeptAndSysuserDto;

import java.util.List;

public interface MathDeptAndSysuserMapper {
    public List<MathDeptAndSysuserDto> mathDeptUserSum();
}

package com.lzj.system.service.impl;

import com.lzj.system.domain.dto.MathDeptAndSysuserDto;
import com.lzj.system.mapper.MathDeptAndSysuserMapper;
import com.lzj.system.service.IMathDeptAndSysuserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class MathDeptAndSysuserServiceImpl implements IMathDeptAndSysuserService {
    @Autowired
    private MathDeptAndSysuserMapper mathDeptAndSysuserMapper;
    @Override
    public List<MathDeptAndSysuserDto> mathDeptUserSum() {

        //获取到所有员工与部门id和部门名称
        List<MathDeptAndSysuserDto> list = mathDeptAndSysuserMapper.mathDeptUserSum();
        //把数据转化成 deptid:对象的形式
        Map<String, MathDeptAndSysuserDto> deptMap = list.stream()
                .collect(Collectors.toMap(MathDeptAndSysuserDto::getDeptId, d -> d));
        //开始双重遍历集合数据，
        for (int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.size(); j++){
                String deptid=list.get(i).getDeptId().trim();
                //如果第一个数据是0，那么就是顶层部门，顶层部门不做计算
                if(!(deptid=="0")){
                    if(!("-1".equals(deptid))){
                        //如果不是，进行下一步操作获取部门id，与其他的id串进行对比，
                        String deptidMassage=list.get(j).getDeptMassage().trim();
                        List<String> deptidMassagelist = Arrays.asList(deptidMassage.split(","));
                        if(deptidMassagelist.contains(deptid)){//这种情况，就要在加到deptMap中了
                            //获取到当前的部门员工数量n
                            deptMap.get(deptid)
                                    .setUserNum(list.get(j)
                                            .getUserNum()+deptMap.get(deptid)
                                            .getUserNum());
                        }
                    }
                }
            }
        }
        return deptMap.values().stream().collect(Collectors.toList());
    }
}

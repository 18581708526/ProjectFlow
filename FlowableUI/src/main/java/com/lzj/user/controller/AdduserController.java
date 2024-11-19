package com.lzj.user.controller;

import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/add")
public class AdduserController {
    @Autowired
    private ProcessEngine processEngine;

    @PostMapping("user")
    public String addUserTest(){
        IdentityService identityService = processEngine.getIdentityService();
        User user = identityService.newUser("x");
        user.setFirstName("x");
        user.setLastName("x");
        user.setPassword("x");
        identityService.saveUser(user);
        identityService.createMembership("x", "common");
        return "添加用户成功！！";
    }
}

package com.psbc.rpa.controller;

import com.psbc.rpa.context.TokenContext;
import com.psbc.rpa.model.Result;
import com.psbc.rpa.model.RpaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author dahua
 * @time 2022/3/7 15:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenContext tokenContext;

    @RequestMapping("/login")
    public Result login() {
        RpaUser rpaUser = new RpaUser();
        rpaUser.setUserId("666666");
        rpaUser.setUsername("dahua");
        rpaUser.setPassword("666666");
        String token = UUID.randomUUID().toString();
        tokenContext.putUser(token, rpaUser);
        return Result.success(token);
    }
}

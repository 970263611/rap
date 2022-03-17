package com.psbc.rpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

/**
 * @author dahua
 * @time 2022/3/7 15:21
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/index")
    public String toIndex() {
        return "index";
    }
}

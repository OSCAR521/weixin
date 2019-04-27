package com.domiyi.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by  OSCAR on 2018/12/19
 */
@Controller
public class TestController {

    @RequestMapping("/index")
    @ResponseBody
    public String say(){
        return "OSCAR";
    }

}

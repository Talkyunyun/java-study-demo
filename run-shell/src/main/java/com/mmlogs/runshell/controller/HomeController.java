package com.mmlogs.runshell.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class HomeController
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
@RestController
public class HomeController {


    @GetMapping("/say")
    public String say() {

        return "测试demo";
    }
}

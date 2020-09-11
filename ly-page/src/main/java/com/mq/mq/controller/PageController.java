package com.mq.mq.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {
    /**
     * 跳转到商品详情页
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(Model model, @PathVariable("id") Long id) {

        return "item";
    }
}

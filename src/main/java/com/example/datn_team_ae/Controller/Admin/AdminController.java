package com.example.datn_team_ae.Controller.Admin;


import com.example.datn_team_ae.Service.KhachHangService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private KhachHangService khachHangService;
    @GetMapping
    public String index() {
        return "redirect:/admin/";
    }
    @RequestMapping("/")
    public String admin() {
        return "Admin/index";
    }
}

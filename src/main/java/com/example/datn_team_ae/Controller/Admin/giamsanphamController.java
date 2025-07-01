package com.example.datn_team_ae.Controller.Admin;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/phieugiamsp")

public class giamsanphamController {
    @GetMapping("")
    public String hienThiTrangHTML() {
        return "Admin/PhieuGiamGia/phieugiamsp";
    }
}

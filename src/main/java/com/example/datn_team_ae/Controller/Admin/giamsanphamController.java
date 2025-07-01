package com.example.datn_team_ae.Controller.Admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/phieugiamsp")

public class giamsanphamController {
    @GetMapping("")
    public String hienThiTrangHTML() {
        return "Admin/PhieuGiamGia/phieugiamsp";
    }
}

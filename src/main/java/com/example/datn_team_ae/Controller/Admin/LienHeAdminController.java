package com.example.datn_team_ae.Controller.Admin;

import com.example.datn_team_ae.Entity.TrangThaiLienHe;
import com.example.datn_team_ae.Service.LienHeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/lien-he")
@RequiredArgsConstructor
public class LienHeAdminController {

    private final LienHeService lienHeService;

    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("dsLienHe", lienHeService.layTatCa());

        model.addAttribute("tongLienHe", lienHeService.demTatCa());
        model.addAttribute("chuaXuLy", lienHeService.demTheoTrangThai(TrangThaiLienHe.CHUA_XU_LY));
        model.addAttribute("dangXuLy", lienHeService.demTheoTrangThai(TrangThaiLienHe.DANG_XU_LY));
        model.addAttribute("daXuLy", lienHeService.demTheoTrangThai(TrangThaiLienHe.DA_XU_LY));

        return "Admin/LienHe/lienhe";
    }

    @PostMapping("/cap-nhat")
    public String capNhatTrangThai(@RequestParam Long id,
                                   @RequestParam("trangThai") String trangThaiStr,
                                   RedirectAttributes ra) {
        try {
            TrangThaiLienHe trangThai = TrangThaiLienHe.valueOf(trangThaiStr);
            lienHeService.capNhatTrangThai(id, trangThai);
            ra.addFlashAttribute("message", "Cập nhật trạng thái thành công");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("message", "Trạng thái không hợp lệ");
        }
        return "redirect:/admin/lien-he";
    }

    @PostMapping("/xoa")
    public String xoaLienHe(@RequestParam Long id, RedirectAttributes ra) {
        lienHeService.xoaLienHe(id);
        ra.addFlashAttribute("message", "Đã xóa liên hệ");
        return "redirect:/admin/lien-he";
    }
}

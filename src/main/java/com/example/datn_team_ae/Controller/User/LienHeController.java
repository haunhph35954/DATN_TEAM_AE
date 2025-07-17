package com.example.datn_team_ae.Controller.User;

import com.example.datn_team_ae.DTO.LienHeDTO;
import com.example.datn_team_ae.Service.LienHeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lien-he")
@RequiredArgsConstructor
public class LienHeController {
    private final LienHeService lienHeService;
//
//    @PostMapping("/add")
//    public String xuLyLienHe(@ModelAttribute LienHeDTO dto, RedirectAttributes redirectAttributes) {
//        lienHeService.guiLienHe(dto); // dùng service cũ
//        redirectAttributes.addFlashAttribute("message", "Gửi liên hệ thành công!");
//        return "redirect:/lien-he"; // quay về trang liên hệ
//    }
//
//    @GetMapping
//    public String hienThiLienHe() {
//        return "User/lienhe";
//    }
@PostMapping("/add")
public String xuLyLienHe(@ModelAttribute("dto") @Valid LienHeDTO dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
        // Gửi lại form với dữ liệu nhập + lỗi validate
        redirectAttributes.addFlashAttribute("dto", dto);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dto", result);
        return "redirect:/lien-he";
    }

    lienHeService.guiLienHe(dto);
    redirectAttributes.addFlashAttribute("message", "Gửi liên hệ thành công!");
    return "redirect:/lien-he";
}
    @GetMapping
    public String hienThiLienHe(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new LienHeDTO());
        }
        return "User/lienhe";
    }

}

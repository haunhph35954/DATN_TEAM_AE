package com.example.datn_team_ae.Controller.User;

import com.example.datn_team_ae.DTO.SanPhamTuVanDTO;
import com.example.datn_team_ae.Repository.SanPhamChiTietRepository;
import com.example.datn_team_ae.Service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;

    @PostMapping("/hoi")
    public ResponseEntity<String> traLoiKhach(@RequestBody String cauHoi) {
        try {

            List<SanPhamTuVanDTO> danhSachSanPham = sanPhamChiTietRepository.laySanPhamTuVan();

            String traLoi = geminiService.hoiGemini(cauHoi, danhSachSanPham);

            return ResponseEntity.ok(traLoi);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("⚠️ Xin lỗi, chatbot gặp lỗi. Vui lòng thử lại sau, liên hệ đội ngũ Minlina để được hỗ trợ");
        }
    }
}

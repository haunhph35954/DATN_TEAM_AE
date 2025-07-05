package com.example.datn_team_ae.API;

import com.example.datn_team_ae.DTO.LienHeDTO;
import com.example.datn_team_ae.Entity.LienHe;
import com.example.datn_team_ae.Entity.TrangThaiLienHe;
import com.example.datn_team_ae.Service.LienHeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lien-he")
@RequiredArgsConstructor
public class LienHeApiController {

    private final LienHeService lienHeService;

    @PostMapping("/add")
    public ResponseEntity<LienHe> them(@ModelAttribute @Valid LienHeDTO dto) {
        return ResponseEntity.ok(lienHeService.guiLienHe(dto));
    }


    @GetMapping
    public ResponseEntity<List<LienHe>> getAll() {
        return ResponseEntity.ok(lienHeService.layTatCa());
    }

    @PutMapping("/{id}/trang-thai")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam TrangThaiLienHe trangThai) {
        lienHeService.capNhatTrangThai(id, trangThai);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        lienHeService.xoaLienHe(id);
        return ResponseEntity.noContent().build();
    }
}

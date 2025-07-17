package com.example.datn_team_ae.Service;

import com.example.datn_team_ae.DTO.LienHeDTO;
import com.example.datn_team_ae.Entity.LienHe;
import com.example.datn_team_ae.Entity.TrangThaiLienHe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LienHeService {
    long demTatCa();
    long demTheoTrangThai(TrangThaiLienHe trangThai);

    LienHe guiLienHe(LienHeDTO dto);
    List<LienHe> layTatCa();
    Optional<LienHe> timTheoId(Long id);
    void capNhatTrangThai(Long id, TrangThaiLienHe trangThai);
    void xoaLienHe(Long id);
}


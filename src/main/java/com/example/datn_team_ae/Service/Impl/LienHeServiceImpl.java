package com.example.datn_team_ae.Service.Impl;

import com.example.datn_team_ae.DTO.LienHeDTO;
import com.example.datn_team_ae.Entity.LienHe;
import com.example.datn_team_ae.Entity.TrangThaiLienHe;
import com.example.datn_team_ae.Repository.LienHeRepository;
import com.example.datn_team_ae.Service.EmailService;
import com.example.datn_team_ae.Service.LienHeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LienHeServiceImpl implements LienHeService {

    private final LienHeRepository lienHeRepo;
    private final EmailService emailService;

    @Override
    public LienHe guiLienHe(LienHeDTO dto) {
        LienHe lh = new LienHe();
        lh.setHoTen(dto.getHoTen());
        lh.setEmail(dto.getEmail());
        lh.setTieuDe(dto.getTieuDe());
        lh.setNoiDung(dto.getNoiDung());

        LienHe saved = lienHeRepo.save(lh);

        emailService.sendConfirmationToCustomer(saved);
        emailService.sendNotificationToAdmin(saved);

        return saved;
    }

    @Override
    public List<LienHe> layTatCa() {
        return lienHeRepo.findAll();
    }

    @Override
    public Optional<LienHe> timTheoId(Long id) {
        return lienHeRepo.findById(id);
    }

    @Override
    public void capNhatTrangThai(Long id, TrangThaiLienHe trangThai) {
        LienHe lh = lienHeRepo.findById(id).orElseThrow();
        lh.setTrangThai(trangThai);
        lienHeRepo.save(lh);
    }

    @Override
    public void xoaLienHe(Long id) {
        lienHeRepo.deleteById(id);
    }
}

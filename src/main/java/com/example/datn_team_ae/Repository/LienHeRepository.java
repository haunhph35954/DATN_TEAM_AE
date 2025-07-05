package com.example.datn_team_ae.Repository;

import com.example.datn_team_ae.Entity.LienHe;
import com.example.datn_team_ae.Entity.TrangThaiLienHe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LienHeRepository extends JpaRepository<LienHe, Long> {
    List<LienHe> findByTrangThai(TrangThaiLienHe trangThai);

}
package com.example.datn_team_ae.Repository;

import com.example.datn_team_ae.Entity.SanPham;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham,Integer> {

    Optional<SanPham> findByTenSanPham(String tenSanPham);


    List<SanPham> findByTrangThai(String trangThai, Sort sort);

    List<SanPham> findByDeleted(Boolean deleted,Sort sort);
    Integer countByTrangThai(String trangThai);

    @Modifying
    @Query("UPDATE SanPham sp SET sp.soLuong = :totalStock WHERE sp.id = :idSanPham")
    void updateStock(@Param("idSanPham") Integer idSanPham, @Param("totalStock") Integer totalStock);
    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN true ELSE false END FROM SanPham sp " +
            "WHERE sp.tenSanPham = :tenSanPham " +
            "AND sp.thuongHieu.id = :thuongHieuId " +
            "AND sp.xuatXu.id = :xuatXuId " +
            "AND sp.danhMuc.id = :danhMucId " +
            "AND sp.chatLieu.id = :chatLieuId")
    boolean existsByAllFields(
            @Param("tenSanPham") String tenSanPham,
            @Param("thuongHieuId") Integer thuongHieuId,
            @Param("xuatXuId") Integer xuatXuId,
            @Param("danhMucId") Integer danhMucId,
            @Param("chatLieuId") Integer chatLieuId
    );

}

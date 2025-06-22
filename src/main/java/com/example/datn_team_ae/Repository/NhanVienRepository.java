//package com.example.datn_team_ae.Repository;
//
//import com.example.datn_team_ae.Entity.NhanVien;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
//    Optional<NhanVien> findByEmail(String email); // Đảm bảo trả về Optional
//
//    Optional<NhanVien> findFirstByUsername(String username);
//}

package com.example.datn_team_ae.Repository;

import com.example.datn_team_ae.Entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {

    @Query("SELECT n FROM NhanVien n WHERE LOWER(TRIM(n.email)) = LOWER(TRIM(:email))")
    Optional<NhanVien> findByEmail(@Param("email") String email);
}

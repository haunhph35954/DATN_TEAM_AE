package com.example.datn_team_ae.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lien_he")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LienHe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hoTen;
    private String email;
    private String tieuDe;
    private String noiDung;
    @Column(name = "da_xu_ly", nullable = false)
    private Boolean daXuLy = false; // gán mặc định false

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiLienHe trangThai = TrangThaiLienHe.CHUA_XU_LY;



    private LocalDateTime ngayGui = LocalDateTime.now();
}

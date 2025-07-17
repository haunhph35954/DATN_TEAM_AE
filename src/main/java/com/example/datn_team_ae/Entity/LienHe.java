package com.example.datn_team_ae.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String tieuDe;

    @NotBlank(message = "Nội dung không được để trống")
    private String noiDung;

    @Column(name = "da_xu_ly", nullable = false)
    private Boolean daXuLy = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiLienHe trangThai = TrangThaiLienHe.CHUA_XU_LY;

    private LocalDateTime ngayGui = LocalDateTime.now();
}
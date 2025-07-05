package com.example.datn_team_ae.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LienHeDTO {
    private Long id;

    @NotBlank(message = "Họ tên không được bỏ trống")
    private String hoTen;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được bỏ trống")
    private String email;

    @NotBlank(message = "Tiêu đề không được bỏ trống")
    private String tieuDe;

    @NotBlank(message = "Nội dung không được bỏ trống")
    private String noiDung;


}

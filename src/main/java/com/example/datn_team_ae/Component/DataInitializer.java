//package com.example.datn_team_ae.Component;
//
//import com.example.datn_team_ae.Entity.NhanVien;
//import com.example.datn_team_ae.Repository.NhanVienRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//    @Autowired
//    private NhanVienRepository nhanVienRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        // Kiểm tra xem có tài khoản admin chưa
//        if (nhanVienRepository.findFirstByUsername("admin").isEmpty()) {
//            NhanVien admin = new NhanVien();
//            admin.setUsername("admin");
//            admin.setHoTen("admin");
//            admin.setPassword(passwordEncoder.encode("123456")); // Mã hóa mật khẩu
//            admin.setVaiTro(NhanVien.Role.ADMIN);
//            admin.setEmail("admin@gmail.com");
//            admin.setDiaChi("Hà Nội");
//            admin.setGioiTinh(true);
//            admin.setNgaySinh(null);
//            admin.setTrangThai("Đang Hoạt Động");
//            admin.setHinhAnh(null);
//
//            nhanVienRepository.save(admin);
//            System.out.println("Tài khoản ADMIN đã được tạo.");
//        } else {
//            System.out.println("Tài khoản ADMIN đã tồn tại.");
//        }
//    }
//}
package com.example.datn_team_ae.Component;

import com.example.datn_team_ae.Entity.NhanVien;
import com.example.datn_team_ae.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin@gmail.com";

        // Dùng email để kiểm tra tồn tại
        if (nhanVienRepository.findByEmail(email.trim().toLowerCase()).isEmpty()) {
            NhanVien admin = new NhanVien();
            admin.setUsername("admin");
            admin.setHoTen("Admin");
            admin.setPassword(passwordEncoder.encode("123456")); // Mã hóa mật khẩu
            admin.setVaiTro(NhanVien.Role.ADMIN);
            admin.setEmail(email.trim().toLowerCase()); // chuẩn hóa luôn
            admin.setDiaChi("Hà Nội");
            admin.setGioiTinh(true);
            admin.setNgaySinh(null); // Hoặc đặt ngày mặc định
            admin.setTrangThai("Đang Hoạt Động");
            admin.setHinhAnh(null);

            nhanVienRepository.save(admin);
            System.out.println("✅ Tài khoản ADMIN đã được tạo.");
        } else {
            System.out.println("⚠️ Tài khoản ADMIN đã tồn tại.");
        }
    }
}

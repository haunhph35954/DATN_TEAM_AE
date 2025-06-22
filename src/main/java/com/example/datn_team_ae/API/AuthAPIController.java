//package com.example.datn_team_ae.API;
//
//import com.example.datn_team_ae.Entity.NhanVien;
//import com.example.datn_team_ae.Service.KhachHangService;
//import com.example.datn_team_ae.Service.NhanVienService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
////import javax.servlet.http.Cookie;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class AuthAPIController {
//
//    private final NhanVienService nhanVienService;
//
//    // thêm login  khách hàng
//    private final KhachHangService khachHangService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, HttpSession session, HttpServletResponse response) {
//        Optional<NhanVien> nhanVienOpt = nhanVienService.findByEmail(email);
//
//        if (nhanVienOpt.isPresent()) {
//            NhanVien nhanVien = nhanVienOpt.get();
//            if (passwordEncoder.matches(password, nhanVien.getPassword())) {
//                // Tạo một UUID duy nhất cho phiên làm việc
//                String sessionId = UUID.randomUUID().toString(); // Tạo UUID ngẫu nhiên
//
//                // Tạo cookie với session ID duy nhất
//                Cookie sessionCookie = new Cookie("SESSION_" + sessionId, sessionId);
//                sessionCookie.setMaxAge(60 * 60); // Đặt cookie sống 1 giờ
//                sessionCookie.setPath("/");  // Đặt phạm vi cookie
//                response.addCookie(sessionCookie);  // Thêm cookie vào response
//
//                // Lưu sessionId vào session để sử dụng khi logout
//                session.setAttribute("sessionId", sessionId);
//                session.setAttribute("user", nhanVien);
//                session.setAttribute("role", nhanVien.getVaiTro().toString());
//
//                return ResponseEntity.ok(nhanVien);
//            } else {
//                return ResponseEntity.badRequest().body("Mật khẩu sai!");
//            }
//        }
//        return ResponseEntity.badRequest().body("Sai email hoặc mật khẩu!");
//    }
//
//
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session, HttpServletResponse response) {
//        // Lấy sessionId từ session
//        String sessionId = (String) session.getAttribute("sessionId");
//
//        if (sessionId != null) {
//            // Hủy session hiện tại
//            session.invalidate();  // Hủy session
//
//            // Xóa cookie bằng cách sử dụng sessionId
//            Cookie sessionCookie = new Cookie("SESSION_" + sessionId, null);
//            sessionCookie.setMaxAge(0);  // Đặt tuổi cookie là 0 để xóa nó
//            sessionCookie.setPath("/");  // Đặt phạm vi cookie
//            response.addCookie(sessionCookie);  // Xóa cookie trên trình duyệt
//
//            return ResponseEntity.ok("Đăng xuất thành công!");
//        }
//        return ResponseEntity.status(401).body("Chưa đăng nhập!");
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<?> getCurrentUser(HttpSession session) {
//        NhanVien nhanVien = (NhanVien) session.getAttribute("user");
//
//        if (nhanVien != null) {
//            return ResponseEntity.ok(nhanVien);
//        }
//        return ResponseEntity.status(401).body("Chưa đăng nhập!");
//    }
//}
//package com.example.datn_team_ae.API;
//
//import com.example.datn_team_ae.Entity.KhachHang;
//import com.example.datn_team_ae.Entity.NhanVien;
//import com.example.datn_team_ae.Service.KhachHangService;
//import com.example.datn_team_ae.Service.NhanVienService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class AuthAPIController {
//
//    private final NhanVienService nhanVienService;
//    private final KhachHangService khachHangService;
//    private final BCryptPasswordEncoder passwordEncoder;

    //    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @RequestParam String email,
//            @RequestParam String password,
//            HttpSession session,
//            HttpServletResponse response) {
//
//        // Chuẩn hóa email (tránh lỗi nhập sai do khoảng trắng, viết hoa)
//        String normalizedEmail = email.trim().toLowerCase();
//
//        Optional<NhanVien> nhanVienOpt = nhanVienService.findByEmail(normalizedEmail);
//
//        if (nhanVienOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Tài khoản không tồn tại trong hệ thống!");
//        }
//
//        NhanVien nhanVien = nhanVienOpt.get();
//
//        // So khớp mật khẩu đã mã hóa
//        if (!passwordEncoder.matches(password, nhanVien.getPassword())) {
//            return ResponseEntity.badRequest().body("Mật khẩu không đúng!");
//        }
//
//        // Tạo UUID sessionId
//        String sessionId = UUID.randomUUID().toString();
//
//        // Tạo cookie lưu sessionId
//        Cookie sessionCookie = new Cookie("SESSION_" + sessionId, sessionId);
//        sessionCookie.setMaxAge(60 * 60); // 1 giờ
//        sessionCookie.setPath("/");
//        response.addCookie(sessionCookie);
//
//        // Lưu session
//        session.setAttribute("sessionId", sessionId);
//        session.setAttribute("user", nhanVien);
//        session.setAttribute("role", nhanVien.getVaiTro().toString());
//
//        return ResponseEntity.ok(nhanVien);
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session, HttpServletResponse response) {
//        String sessionId = (String) session.getAttribute("sessionId");
//
//        if (sessionId != null) {
//            session.invalidate();
//
//            Cookie sessionCookie = new Cookie("SESSION_" + sessionId, null);
//            sessionCookie.setMaxAge(0); // Xóa cookie
//            sessionCookie.setPath("/");
//            response.addCookie(sessionCookie);
//
//            return ResponseEntity.ok("Đăng xuất thành công!");
//        }
//
//        return ResponseEntity.status(401).body("Chưa đăng nhập!");
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<?> getCurrentUser(HttpSession session) {
//        NhanVien nhanVien = (NhanVien) session.getAttribute("user");
//
//        if (nhanVien != null) {
//            return ResponseEntity.ok(nhanVien);
//        }
//
//        return ResponseEntity.status(401).body("Chưa đăng nhập!");
//    }
//}
package com.example.datn_team_ae.API;

import com.example.datn_team_ae.Entity.KhachHang;
import com.example.datn_team_ae.Entity.NhanVien;
import com.example.datn_team_ae.Service.KhachHangService;
import com.example.datn_team_ae.Service.NhanVienService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthAPIController {

    private final NhanVienService nhanVienService;
    private final KhachHangService khachHangService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            HttpServletResponse response) {

        String normalizedEmail = email.trim().toLowerCase();

        // ========= Đăng nhập NHÂN VIÊN =========
        Optional<NhanVien> nhanVienOpt = nhanVienService.findByEmail(normalizedEmail);
        if (nhanVienOpt.isPresent()) {
            NhanVien nv = nhanVienOpt.get();

            if (passwordEncoder.matches(password, nv.getPassword())) {
                createSession(session, response, nv, nv.getVaiTro().toString());
                System.out.println("✅ Đăng nhập thành công - Nhân viên: " + nv.getEmail());
                return ResponseEntity.ok("ADMIN");
            } else {
                System.out.println("❌ Sai mật khẩu nhân viên: " + email);
                return ResponseEntity.badRequest().body("Mật khẩu sai!");
            }
        }

        // ========= Đăng nhập KHÁCH HÀNG =========
        Optional<KhachHang> khOpt = khachHangService.findByEmail(normalizedEmail);
        if (khOpt.isPresent()) {
            KhachHang kh = khOpt.get();

            if (passwordEncoder.matches(password, kh.getPassword())) {
                createSession(session, response, kh, "KHACH_HANG");
                System.out.println("✅ Đăng nhập thành công - Khách hàng: " + kh.getEmail());
                return ResponseEntity.ok("KHACH_HANG");
            } else {
                System.out.println("❌ Sai mật khẩu khách hàng: " + email);
                return ResponseEntity.badRequest().body("Mật khẩu sai!");
            }
        }

        // ========= Không tìm thấy tài khoản =========
        System.out.println("❌ Không tìm thấy tài khoản: " + normalizedEmail);
        return ResponseEntity.badRequest().body("Tài khoản không tồn tại!");
    }

    private void createSession(HttpSession session, HttpServletResponse response, Object user, String role) {
        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
        sessionCookie.setMaxAge(60 * 60);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);

        session.setAttribute("sessionId", sessionId);
        session.setAttribute("user", user);
        session.setAttribute("role", role);
    }
}

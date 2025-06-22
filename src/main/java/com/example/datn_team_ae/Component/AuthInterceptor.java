package com.example.datn_team_ae.Component;

import com.example.datn_team_ae.Entity.KhachHang;
import com.example.datn_team_ae.Entity.NhanVien;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        NhanVien userNhanVien = (NhanVien) session.getAttribute("userNhanVien");
        KhachHang userKhachHang = (KhachHang) session.getAttribute("userKhachHang");

        // Kiểm tra nếu userNhanVien là null
        if (userNhanVien == null) {
            String requestURI = request.getRequestURI();

            if (requestURI.startsWith("/auth/home") || requestURI.startsWith("/admin")) {
                response.sendRedirect("/auth/home");
            } else {
                response.sendRedirect("/auth/minlina");
            }
            return false;
        }

//        // Kiểm tra nếu userKhachHang là null và người dùng đang truy cập các trang yêu cầu
//        if (userKhachHang == null) {
//            String requestURI = request.getRequestURI();
//
//            if (requestURI.startsWith("/trang-chu") ||
//                    requestURI.startsWith("/don-hang") ||
//                    requestURI.startsWith("/thong-tin") ||
//                    requestURI.startsWith("/dia-chi")) {
//                response.sendRedirect("/trang-chu");
//            } else {
//                response.sendRedirect("/trang-chu");
//            }
//            return false;
//        }

        // Kiểm tra phân quyền nếu cần
        return true;
    }
}

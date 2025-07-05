package com.example.datn_team_ae.Service;

import com.example.datn_team_ae.Entity.LienHe;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Thay bằng email đúng đã cấu hình trong application.properties
    private static final String FROM_EMAIL = "haunguyen2k411@gmail.com";

    public void sendConfirmationToCustomer(LienHe lienHe) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL); // BẮT BUỘC THÊM
        message.setTo(lienHe.getEmail());
        message.setSubject("✅ Cảm ơn bạn đã liên hệ với MinLiNa!");
        message.setText("Chào " + lienHe.getHoTen() + ",\n\n"
                + "Chúng tôi đã nhận được liên hệ của bạn với tiêu đề: \"" + lienHe.getTieuDe() + "\".\n\n"
                + "Nội dung: " + lienHe.getNoiDung() + "\n\n"
                + "Chúng tôi sẽ phản hồi trong thời gian sớm nhất.\n\n"
                + "Trân trọng,\nMinLiNa Team");

        mailSender.send(message);
    }

    public void sendNotificationToAdmin(LienHe lienHe) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL); // BẮT BUỘC THÊM
        message.setTo("nghuuhau2k4@gmail.com");
        message.setSubject("📬 Liên hệ mới từ khách hàng: " + lienHe.getHoTen());
        message.setText("Bạn có một liên hệ mới:\n\n"
                + "👤 Họ tên: " + lienHe.getHoTen() + "\n"
                + "📧 Email: " + lienHe.getEmail() + "\n"
                + "📌 Tiêu đề: " + lienHe.getTieuDe() + "\n"
                + "✉️ Nội dung: " + lienHe.getNoiDung() + "\n\n"
                + "Vui lòng kiểm tra và xử lý liên hệ này sớm.");

        mailSender.send(message);
    }
}


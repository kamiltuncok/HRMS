package kodlamaio.HRMS.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendPasswordResetEmail(String toEmail, String rawToken) {
        try {
            String resetLink = frontendUrl + "/reset-password?token=" + rawToken;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Şifre Sıfırlama Talebi - HRMS");
            message.setText(
                "Merhaba,\n\n" +
                "Hesabınız için bir şifre sıfırlama talebi aldık.\n\n" +
                "Şifrenizi sıfırlamak için aşağıdaki bağlantıya tıklayın:\n" +
                resetLink + "\n\n" +
                "Bu bağlantı 30 dakika süreyle geçerlidir.\n\n" +
                "Eğer bu isteği siz yapmadıysanız bu maili dikkate almayın. " +
                "Hesabınız güvende olmaya devam edecektir.\n\n" +
                "Saygılarımızla,\n" +
                "HRMS Ekibi"
            );

            mailSender.send(message);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
        }
    }
}

package quangnnfx16178.ilovevn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.User;

@Service("EmailService")
@RequiredArgsConstructor
@ComponentScan(basePackages = { "quangnnfx16178.ilovevn.config" })
public class EmailServiceImpl implements EmailService {
//    private static final String NOREPLY_ADDRESS = "noreply@ilovevn.funix.edu.vn";

    private final JavaMailSender emailSender;
    private final SimpleMailMessage defaultPasswordTemplate;


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendDefaultPasswordToNewUser(User user, String password) {
        String text = String.format(defaultPasswordTemplate.getText(), user.getFullName(), user.getEmail(), password);
        sendSimpleMessage(user.getEmail(), "ilovevn - Đăng ký tài khoản thành công", text);
    }
}

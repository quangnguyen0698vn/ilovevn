package quangnnfx16178.ilovevn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.User;

@Service("EmailService")
@ComponentScan(basePackages = { "quangnnfx16178.ilovevn.config" })
public class EmailServiceImpl implements EmailService {
//    private static final String NOREPLY_ADDRESS = "noreply@ilovevn.funix.edu.vn";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    @Qualifier("defaultPasswordEmailMessage")
    private SimpleMailMessage defaultPasswordTemplate;

    @Autowired
    @Qualifier("resetPasswordTokenMessage")
    private SimpleMailMessage resetTokenTemplate;

    @Autowired
    @Qualifier("donateStateAcceptedMessage")
    private SimpleMailMessage donateStateAcceptedMessageTemplate;

    @Autowired
    @Qualifier("donateStateRejectedMessage")
    private SimpleMailMessage donateStateRejectedMessageTemplate;


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

    @Override
    public void sendResetTokenEmail(User user, String token) {
        String text = String.format(resetTokenTemplate.getText(), user.getFullName(), token);
        sendSimpleMessage(user.getEmail(), "ilovevn - Reset Password Token", text);
    }

    @Override
    public void sendDonateAcceptedStateEmail(Donation donation) {
        User user = donation.getUser();
        if (user == null) return;
        String text = String.format(donateStateAcceptedMessageTemplate.getText(), user.getFullName(), donation.getTransRefNo(), donation.getAmount(), donation.getProject().getName(), donation.getMessage());
        sendSimpleMessage(user.getEmail(), "Bạn đã quyên góp thành công cho dự án " + donation.getProject().getName(), text);
    }

    @Override
    public void sendDonateRejectedStateEmail(Donation donation) {
        User user = donation.getUser();
        if (user == null) return;
        String text = String.format(donateStateRejectedMessageTemplate.getText(), user.getFullName(), donation.getTransRefNo(), donation.getAmount(), donation.getProject().getName(), donation.getMessage());
        sendSimpleMessage(user.getEmail(), "Xin kiểm tra lại thông tin quyên góp cho dự án " + donation.getProject().getName(), text);
    }
}

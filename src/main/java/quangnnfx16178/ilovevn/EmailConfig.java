package quangnnfx16178.ilovevn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource(value={"classpath:application.properties"})
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String mailServerHost;

    @Value("${spring.mail.port}")
    private Integer mailServerPort;

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean(name = "defaultPasswordEmailMessage")
    public SimpleMailMessage notifyDefaultPasswordMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Xin chào bạn %s\n" +
                        "Chào mừng bạn đến với hệ thống quyên góp từ thiện ilovevn!\n" +
                        "Sau đây là thông tin đăng nhập của bạn: \n" +
                        "- Email: %s\n" +
                        "- Mật khẩu: %s\n" +
                        "Xin vui lòng đổi mật khẩu ngay sau lần đăng nhập đầu tiên!"
        );
        return message;
    }

    @Bean(name = "resetPasswordTokenMessage")
    public SimpleMailMessage notifyResetPasswordTokenMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Xin chào bạn %s\n" +
                        "Hệ thống ghi nhận bạn đang thực hiện tính năng Forgot Password!\n" +
                        "Để tiếp tục lấy lại mật khẩu, bạn cần sử dụng token dưới đây\n" +
                        "- Token: %s\n" +
                                "Token sẽ hết hạn sau 10 phút\n" +
                        "Nếu bạn không hề sử dụng tính năng thay đổi mật khẩu, hãy bỏ qua email này!\n" +
                                "Trân trọng cảm ơn!"
        );
        return message;
    }
}

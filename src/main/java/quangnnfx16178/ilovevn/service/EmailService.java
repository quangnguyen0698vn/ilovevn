package quangnnfx16178.ilovevn.service;

import quangnnfx16178.ilovevn.entity.User;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendDefaultPasswordToNewUser(User user, String password);
}

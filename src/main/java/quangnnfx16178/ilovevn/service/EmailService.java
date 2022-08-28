package quangnnfx16178.ilovevn.service;

import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.User;

public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendDefaultPasswordToNewUser(User user, String password);

    void sendResetTokenEmail(User user, String token);

    void sendDonateAcceptedStateEmail(Donation donation);

    void sendDonateRejectedStateEmail(Donation donation);
}

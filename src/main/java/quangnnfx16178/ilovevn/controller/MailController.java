package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import quangnnfx16178.ilovevn.service.EmailServiceImpl;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final EmailServiceImpl emailService;

    @RequestMapping("/sendEmail")
    public String sendEmail(Model model) {
        emailService.sendSimpleMessage("quangnguyen0698vn@gmail.com", "This is my first email", "This is the content");
        model.addAttribute("message", "Email testing");
        return "index";
    }
}

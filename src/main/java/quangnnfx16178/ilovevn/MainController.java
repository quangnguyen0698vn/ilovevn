package quangnnfx16178.ilovevn;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import quangnnfx16178.ilovevn.entity.UserDTO;
import quangnnfx16178.ilovevn.security.MyUserDetails;

import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j2
public class MainController {

    @GetMapping("/login")
    public String viewLoginPage(@SessionAttribute(name = "error", required = false) String errorStr,
                                @SessionAttribute(name = "message", required = false) String message,
                                Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean error = errorStr != null && errorStr.equals("true");
//        log.info("this controller is called");
//        log.info("errorStr = " + errorStr);
//        log.info("error = " + error);
//        log.info("message = " + message);
        if (error || authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if (error) {
                model.addAttribute("error", true);
                model.addAttribute("message", message);
                request.getSession().invalidate();
            }
            return "login_form";
        } else {
            log.info(authentication.getPrincipal());
//            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
//            log.info(user.getFullName());
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String createNewUserForm(Model model) {
        model.addAttribute("title", "Đăng ký tài khoản");
        return "registration_form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/changeUserInfo")
    public String changeUserInfoForm(Model model, @AuthenticationPrincipal MyUserDetails user) {
        model.addAttribute("title", "Thay đổi thông tin");
        model.addAttribute("user", new UserDTO(user.getUser()));
        return "registration_form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/changePassword")
    public String changePassForm(Model model, @AuthenticationPrincipal MyUserDetails user) {
        model.addAttribute("title", "Thay đổi Mật khẩu");
        model.addAttribute("user", new UserDTO(user.getUser()));
        return "change_password_form";
    }

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/charity")
    public String viewCharity() { return "index"; }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "index";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/admin_dashboard";
    }

    @GetMapping("/admin/donations")
    public String adminDonations() {
        return "admin/admin_donations";
    }

    @GetMapping("admin/charities")
    public String adminCharities() {
        return "admin/admin_charities";
    }
}

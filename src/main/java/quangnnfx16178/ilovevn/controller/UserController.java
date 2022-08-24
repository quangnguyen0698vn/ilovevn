package quangnnfx16178.ilovevn.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import quangnnfx16178.ilovevn.entity.Role;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.entity.UserDTO;
import quangnnfx16178.ilovevn.exception.UserNotFoundException;
import quangnnfx16178.ilovevn.security.MyUserDetails;
import quangnnfx16178.ilovevn.service.RoleService;
import quangnnfx16178.ilovevn.service.UserService;
import quangnnfx16178.ilovevn.util.FileUploadUtil;
import quangnnfx16178.ilovevn.util.RandomStringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("repeatedPassword") String repeatedPassword,
                                Model model, HttpServletRequest request) {

        // assume that password & repeatedPassword is the same (checked by js)

        User user = null;

        try {
            user = userService.getByResetToken(token);
            if (user.isTokenExpired()) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Token đã hết hạn sử dụng!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            String sessionId = request.getSession().getId();
            if (!sessionId.equals(user.getResetPasswordSessionId())) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Session không khớp!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            userService.saveResetPassword(user, password);

            model.addAttribute("resetPasswordSuccess", true);
            model.addAttribute("resetPasswordSuccessMessage", "Tài khoản " + user.getEmail() + " đã thay đổi mật khẩu thành công");
            model.addAttribute("resetPasswordEmail", user.getEmail());
            return "login_form";
        } catch (UserNotFoundException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Token không đúng!");
            model.addAttribute("sendEmail", false);
            return "/resetPasswordForm";
        }

    }

    @PostMapping("/resetPasswordForm")
    public String showResetPasswordForm(HttpServletRequest request, Model model, @RequestParam("resetToken") String token,
                                        @RequestParam("email") String email) {
        String text = "Quên mật khẩu";
        model.addAttribute("title", text);

        // check by session id first

        User userBySessionId = null;

        try {
            userBySessionId = userService.getBySessionId(request.getSession().getId());
            userBySessionId.setResetPasswordAttempt(userBySessionId.getResetPasswordAttempt() + 1);
            userService.saveUser(userBySessionId);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Lỗi Session không khớp! Bạn hãy điền lại form bên dưới để thử lại");
            model.addAttribute("sendEmail", false);
            return "forgot_password_form";
        }

        User user = null;
        try {
            user = userService.getByResetToken(token);

            if (user.isTokenExpired()) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Token đã hết hạn sử dụng!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            String sessionId = request.getSession().getId();
            if (!sessionId.equals(user.getResetPasswordSessionId())) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Session không khớp!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            model.addAttribute("token", token);
            model.addAttribute("user", new UserDTO(user));
            return "reset_password_form";
        } catch (UserNotFoundException e) {

            if (userBySessionId.getResetPasswordAttempt() >= 3) {
                userService.clearForgotPasswordRequest(userBySessionId);
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Bạn đã nhập sai token quá 3 lần!\nHệ thống đã hủy yêu cầu thay đổi mật khẩu của bạn!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Token không đúng!\n" +
                    "Vui lòng thử lại để khôi phục mật khẩu cho tài khoẳn với email: " + email);
            model.addAttribute("sendEmail", true);
            model.addAttribute("email", email);
            return "forgot_password_form";
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(Model model, HttpServletRequest request, @RequestParam("email") String email) {
        String text = "Quên mật khẩu";
        model.addAttribute("title", text);
        model.addAttribute("email", email);
        String token = RandomString.make(30);
        try {
            userService.updateResetToken(email, token, request.getSession().getId());
            model.addAttribute("sendEmail", true);
            model.addAttribute("emailNotFound", false);
            model.addAttribute("sendEmailMessage", "Chúng tôi đã gửi mã token đến email: " + email);

        } catch (UserNotFoundException e) {
            model.addAttribute("sendEmail", false);
            model.addAttribute("emailNotFound", true);
            model.addAttribute("emailNotFoundMessage", "Không tồn tại email này trong hệ thống!");
            return "forgot_password_form";
        }
        return "forgot_password_form";
    }

    @GetMapping("/admin/users/create_new_user")
    public String createUser(Model model) {
        String text = "Tạo mới tài khoản";
        List<Role> roles = roleService.listAll();

        model.addAttribute("title", text);
        model.addAttribute("heading", text);
        model.addAttribute("roles", roles);
        return "/admin/user_form";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("users/saveInfo")
    public String saveEditedUser(@AuthenticationPrincipal MyUserDetails userDetails,
                               @RequestParam("address") String address,
                               @RequestParam("phoneNumber") String phoneNumber,
                               @RequestParam(value = "fileImage", required = false) MultipartFile avatar,
                               HttpServletRequest request) {
        User user = userDetails.getUser();
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        userService.saveEditedUser(user, avatar, request);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/saveChangedPassword")
    public String saveChangedPassword(@AuthenticationPrincipal MyUserDetails userDetails,
                                      @RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("password") String password,
                                      @RequestParam("repeatedPassword") String repeatedPassword,
                                      RedirectAttributes ra)
    {
        User user = userDetails.getUser();
        if (!userService.checkCorrectedPassword(user, oldPassword)) {
            ra.addFlashAttribute("checkCorrectPassword", false);
            ra.addFlashAttribute("checkCorrectPasswordMessage", "Mật khẩu KHÔNG ĐÚNG");
            return "redirect:/changePassword";
        }
        else if (!password.equals(repeatedPassword)) {
            ra.addFlashAttribute("checkRepeatedPassword", false);
            ra.addFlashAttribute("checkRepeatedPasswordMessage", "Mật khẩu KHÔNG KHỚP");
            return "redirect:/changePassword";
        }
        else {
            userService.saveChangedPassword(user, password);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Thay đổi mật khẩu thành công");
            log.info("Tài khoản: " + user.getId() + " thay đổi mật khẩu thành công!");
        }
        return "redirect:/";
    }


    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        User user = null;
        try {
            user = userService.getById(id);
        } catch (UserNotFoundException e) {

            String info = e.getMessage();
            log.error(info);

            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", info);
            return "redirect:/admin/users";
        }
        model.addAttribute("user", new UserDTO(user));

        List<Role> roles = roleService.listAll();
        String text = "Cập nhật thông tin tài khoản mã số: " + id;
        model.addAttribute("title", text);
        model.addAttribute("heading", text);
        model.addAttribute("roles", roles);
        return "/admin/user_form";
    }

    @PostMapping("/admin/users/update")
    public String saveUser(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String phoneNumber,
            @RequestParam Integer roleId,
            @RequestParam Integer enabled,
            @RequestParam(value = "fileImage") MultipartFile avatar,
            Model model,
            RedirectAttributes ra,
            HttpServletRequest request) {

        User user = null;
        if (id != null) {
            // UPDATE
            try {
                user = userService.getById(id);
                user.setAddress(address);
                user.setPhoneNumber(phoneNumber);
                if (user.getRole().getRoleName().equals("USER")) {
                    user.setRole(roleService.getById(roleId));
                    user.setEnabled(enabled == 1);
                }
                userService.save(user);
                String text = "Cập nhật thành công người dùng: " + user.getId();
                log.info(text);
                ra.addFlashAttribute("success", true);
                ra.addFlashAttribute("message", text);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            // NEW USER
            user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            user.setRole(roleService.getById(roleId));
            user.setEnabled(enabled == 1);
            String password = RandomStringUtil.generateRandomPassword(10);
            user.setPassword(password);
            user = userService.registerUser(user, avatar, request);
            String text = "Tạo mới thành công người dùng với mã số: " + user.getId();
            log.info(text);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", text);
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/create_new_user")
    public String createNewUserSumbit(@RequestParam String fullName,
                                      @RequestParam String email,
//                                      @RequestParam String password,
                                      @RequestParam String address,
                                      @RequestParam String phoneNumber,
                                      @RequestParam(value = "fileImage") MultipartFile avatar,
                                      Model model,
                                      HttpServletRequest request) {
        log.info("fullName = " + fullName);
        log.info("email = " + email);
        String password = RandomStringUtil.generateRandomPassword(10);
//        log.info("password = " + password);
        log.info("address = " + address);
        log.info("phoneNumber = " + phoneNumber);

        userService.registerUser(fullName, email, password, address, phoneNumber, avatar, request);

        model.addAttribute("message", "Create new user");
        return "redirect:/";
    }

    @GetMapping("/admin/users")
    public String listUsers() {
        return "admin/admin_users";
    }

    @GetMapping("/admin/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled, RedirectAttributes ra, HttpServletRequest request) {

        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "đang hoạt động" : "không hoạt động";
        String info = "Tài khoản có mã số " + id + " đã chuyển sang trạng thái " + status +  " thành công!";
        log.info(info);

        String text = (String) request.getAttribute("message");
        if (text != null) {
            ra.addFlashAttribute("success0", (Boolean) request.getAttribute("success"));
            ra.addFlashAttribute("message0", text);
        }

        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("message", info);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra, HttpServletRequest request) {
        try {
            userService.deleteUserById(id);
//            ProjectSaveUtil.deleteProjecImagesFolder(id, request.getSession().getServletContext().getRealPath("/"));
            String info = "Xóa thành công người dùnng với mã số " + id + " khỏi hệ thống";
            log.info("Xóa thành công người dùnng với mã số " + id + " khỏi hệ thống");
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", info);

            String realPath = request.getSession().getServletContext().getRealPath("/");
            String uploadDir = realPath + "WEB-INF/images/" + "user-images/" + id;
            FileUploadUtil.removeDir(uploadDir);

            return "redirect:/admin/users";
        } catch (UserNotFoundException | IllegalArgumentException e) {
            String info = e.getMessage();
            log.error(info);
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", info);
            return "redirect:/admin/users";
        } catch (DataIntegrityViolationException e) {
            String info = "Xoá thất bại vì dữ liệu người dùng đang được sử dụng (User ID: " + id + ")";
            log.error(info);
            request.setAttribute("success", false);
            request.setAttribute("message", info);
            return "forward:/admin/users/" + id + "/enabled/false";
        }
    }
}

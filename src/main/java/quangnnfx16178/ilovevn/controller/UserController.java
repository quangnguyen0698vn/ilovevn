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
import quangnnfx16178.ilovevn.entity.AuthenticationType;
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
                model.addAttribute("errorMessage", "Token ???? h???t h???n s??? d???ng!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            String sessionId = request.getSession().getId();
            if (!sessionId.equals(user.getResetPasswordSessionId())) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Session kh??ng kh???p!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            userService.saveResetPassword(user, password);

            model.addAttribute("resetPasswordSuccess", true);
            model.addAttribute("resetPasswordSuccessMessage", "T??i kho???n " + user.getEmail() + " ???? thay ?????i m???t kh???u th??nh c??ng");
            model.addAttribute("resetPasswordEmail", user.getEmail());
            return "login_form";
        } catch (UserNotFoundException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Token kh??ng ????ng!");
            model.addAttribute("sendEmail", false);
            return "/resetPasswordForm";
        }

    }

    @PostMapping("/resetPasswordForm")
    public String showResetPasswordForm(HttpServletRequest request, Model model, @RequestParam("resetToken") String token,
                                        @RequestParam("email") String email) {
        String text = "Qu??n m???t kh???u";
        model.addAttribute("title", text);

        // check by session id first

        User userBySessionId = null;

        try {
            userBySessionId = userService.getBySessionId(request.getSession().getId());
            userBySessionId.setResetPasswordAttempt(userBySessionId.getResetPasswordAttempt() + 1);
            userService.saveUser(userBySessionId);
        } catch (UserNotFoundException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "L???i Session kh??ng kh???p! B???n h??y ??i???n l???i form b??n d?????i ????? th??? l???i");
            model.addAttribute("sendEmail", false);
            return "forgot_password_form";
        }

        User user = null;
        try {
            user = userService.getByResetToken(token);

            if (user.isTokenExpired()) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Token ???? h???t h???n s??? d???ng!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            String sessionId = request.getSession().getId();
            if (!sessionId.equals(user.getResetPasswordSessionId())) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Session kh??ng kh???p!");
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
                model.addAttribute("errorMessage", "B???n ???? nh???p sai token qu?? 3 l???n!\nH??? th???ng ???? h???y y??u c???u thay ?????i m???t kh???u c???a b???n!");
                model.addAttribute("sendEmail", false);
                return "forgot_password_form";
            }

            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Token kh??ng ????ng!\n" +
                    "Vui l??ng th??? l???i ????? kh??i ph???c m???t kh???u cho t??i kho???n v???i email: " + email);
            model.addAttribute("sendEmail", true);
            model.addAttribute("email", email);
            return "forgot_password_form";
        }
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordProcess(Model model, HttpServletRequest request, @RequestParam("email") String email) {
        String text = "Qu??n m???t kh???u";
        model.addAttribute("title", text);
        model.addAttribute("email", email);
        String token = RandomString.make(30);
        try {
            userService.updateResetToken(email, token, request.getSession().getId());
            model.addAttribute("sendEmail", true);
            model.addAttribute("emailNotFound", false);
            model.addAttribute("sendEmailMessage", "Ch??ng t??i ???? g???i m?? token ?????n email: " + email);

        } catch (UserNotFoundException e) {
            model.addAttribute("sendEmail", false);
            model.addAttribute("emailNotFound", true);
            model.addAttribute("emailNotFoundMessage", "Kh??ng t???n t???i email n??y trong h??? th???ng!");
            return "forgot_password_form";
        }
        return "forgot_password_form";
    }

    @GetMapping("/admin/users/create_new_user")
    public String createUser(Model model) {
        String text = "T???o m???i t??i kho???n";
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
            ra.addFlashAttribute("checkCorrectPasswordMessage", "M???t kh???u KH??NG ????NG");
            return "redirect:/changePassword";
        }
        else if (!password.equals(repeatedPassword)) {
            ra.addFlashAttribute("checkRepeatedPassword", false);
            ra.addFlashAttribute("checkRepeatedPasswordMessage", "M???t kh???u KH??NG KH???P");
            return "redirect:/changePassword";
        }
        else {
            userService.saveChangedPassword(user, password);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Thay ?????i m???t kh???u th??nh c??ng");
            log.info("T??i kho???n: " + user.getId() + " thay ?????i m???t kh???u th??nh c??ng!");
        }
        return "redirect:/";
    }


    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
                           @RequestParam("readonly") Boolean readonly) {
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
        String text = "C???p nh???t th??ng tin t??i kho???n m?? s???: " + id;
        if (readonly) text = "Xem th??ng tin t??i kho???n m?? s???: " +id;
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
            @RequestParam(value = "fileImage", required = false) MultipartFile avatar,
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
                String text = "C???p nh???t th??nh c??ng ng?????i d??ng: " + user.getId();
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
            user.setAuthenticationType(AuthenticationType.DATABASE);
            user = userService.registerUser(user, avatar, request);
            String text = "T???o m???i th??nh c??ng ng?????i d??ng v???i m?? s???: " + user.getId();
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
                                      RedirectAttributes ra,
                                      HttpServletRequest request) {
        log.info("fullName = " + fullName);
        log.info("email = " + email);
        String password = RandomStringUtil.generateRandomPassword(10);
//        log.info("password = " + password);
        log.info("address = " + address);
        log.info("phoneNumber = " + phoneNumber);

        userService.registerUser(fullName, email, password, address, phoneNumber, avatar, request);

        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("message", "T???o t??i kho???n m???i v???i email " + email + " th??nh c??ng!");
        return "redirect:/";
    }

    @GetMapping("/admin/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled, RedirectAttributes ra, HttpServletRequest request) {

        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "??ang ho???t ?????ng" : "kh??ng ho???t ?????ng";
        String info = "T??i kho???n c?? m?? s??? " + id + " ???? chuy???n sang tr???ng th??i " + status +  " th??nh c??ng!";
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
            String info = "X??a th??nh c??ng ng?????i d??nng v???i m?? s??? " + id + " kh???i h??? th???ng";
            log.info("X??a th??nh c??ng ng?????i d??nng v???i m?? s??? " + id + " kh???i h??? th???ng");
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
            String info = "Xo?? th???t b???i v?? d??? li???u ng?????i d??ng ??ang ???????c s??? d???ng (User ID: " + id + ")";
            log.error(info);
            request.setAttribute("success", false);
            request.setAttribute("message", info);
            return "forward:/admin/users/" + id + "/enabled/false";
        }
    }
}

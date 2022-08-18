package quangnnfx16178.ilovevn.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import quangnnfx16178.ilovevn.exception.UserNotFoundException;
import quangnnfx16178.ilovevn.service.UserService;
import quangnnfx16178.ilovevn.util.RandomStringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/create_new_user")
    public String createNewUserSumbit(@RequestParam String fullName,
                                      @RequestParam String email,
//                                      @RequestParam String password,
                                      @RequestParam String address,
                                      @RequestParam String phoneNumber, Model model) {
        log.info("fullName = " + fullName);
        log.info("email = " + email);
        String password = RandomStringUtil.generateRandomPassword(10);
//        log.info("password = " + password);
        log.info("address = " + address);
        log.info("phoneNumber = " + phoneNumber);

        userService.registerUser(fullName, email, password, address, phoneNumber);

        model.addAttribute("message", "Create new user");
        return "index";
    }

    @GetMapping("/admin/users")
    public String listUsers() {
        return "admin/users";
    }

    @GetMapping("/admin/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled, RedirectAttributes ra, HttpServletRequest request) {

        List<Boolean> success = (ArrayList<Boolean>) request.getAttribute("success");
        List<String> message = (ArrayList<String>) request.getAttribute("message");

        if (success == null) {
            success = new ArrayList<>();
            message = new ArrayList<>();
        }

        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "đang hoạt động" : "không hoạt động";
        String info = "Tài khoản có mã số " + id + " đã chuyển sang trạng thái " + status +  " thành công!";
        success.add(true);
        message.add(info);
        log.info(info);
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("success", success);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra, HttpServletRequest request) {
        List<Boolean> success = new ArrayList<>();
        List<String> message = new ArrayList<>();

        try {
            userService.deleteUserById(id);
//            ProjectSaveUtil.deleteProjecImagesFolder(id, request.getSession().getServletContext().getRealPath("/"));
            String info = "Xóa thành công người dùnng với mã số " + id + " khỏi hệ thống";
            log.info("Xóa thành công người dùnng với mã số " + id + " khỏi hệ thống");
            success.add(true);
            message.add(info);
            ra.addFlashAttribute("success", success);
            ra.addFlashAttribute("message", message);
            return "redirect:/admin/users";
        } catch (UserNotFoundException | IllegalArgumentException e) {
            String info = e.getMessage();
            log.error(info);
            success.add(false);
            message.add(info);
            ra.addFlashAttribute("success", success);
            ra.addFlashAttribute("message", message);
            return "redirect:/admin/users";
        } catch (DataIntegrityViolationException e) {
            String info = "Xoá thất bại vì dữ liệu người dùng đang được sử dụng (User ID: " + id + ")";
            log.error(info);
            success.add(false);
            message.add(info);
            request.setAttribute("success", success);
            request.setAttribute("message", message);
            return "forward:/admin/users/" + id + "/enabled/false";
        }
    }
}

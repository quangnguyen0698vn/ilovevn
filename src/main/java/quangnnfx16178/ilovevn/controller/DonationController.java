package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.exception.DonationNotFoundException;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.security.MyUserDetails;
import quangnnfx16178.ilovevn.service.DonationService;
import quangnnfx16178.ilovevn.service.EmailService;
import quangnnfx16178.ilovevn.service.ProjectService;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;
    private final ProjectService projectService;

    @GetMapping("/admin/donations/{id}/state/{state}")
    public String changeState(@PathVariable("id") Integer id,
                              @PathVariable("state") StateType state, RedirectAttributes ra) {
        log.info("id = " + id);
        log.info("state = " + state.name());
        try {
            donationService.changeState(id, state);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Thay đổi thành công trạng thái của giao dịch: " + id);
        } catch (Exception e) {
            ra.addFlashAttribute("success", false);
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/donations";
    }
    @PostMapping("/donate")
    public String processDonation(
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "trans_ref_no") String transRefNo,
            @RequestParam(value = "message") String message,
            RedirectAttributes ra
    ) {
//        log.info(projectId);
//        log.info(user);
//        log.info(fullName);
//        log.info(amount);
//        log.info(transRefNo);
//        log.info(message);

        Donation donation = new Donation();
        try {
            donation.setProject(projectService.getProjectById(projectId));
        } catch (ProjectNotFoundException e) {
            // never throws
            throw new RuntimeException(e);
        }
        donation.setFullName(fullName);
        donation.setAmount(amount);
        donation.setTransRefNo(transRefNo);
        donation.setMessage(message);
        if (user != null) donation.setUser(user.getUser());
        donation.setState(StateType.PENDING);


        donationService.saveNewDonation(donation);
        ra.addFlashAttribute("success", true);
        ra.addFlashAttribute("message", "Giao dịch quyên góp cho dự án " + donation.getProject().getName() + " của bạn " +
                "đã được hệ thống ghi nhận và sẽ được xử lý trong vòng 24h");
        return "redirect:/";
    }

}

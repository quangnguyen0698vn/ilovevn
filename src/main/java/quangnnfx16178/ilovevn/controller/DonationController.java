package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.security.MyUserDetails;
import quangnnfx16178.ilovevn.service.DonationService;
import quangnnfx16178.ilovevn.service.ProjectService;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;
    private final ProjectService projectService;

    @PostMapping("/donate")
    public String processDonation(
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "fullName") String fullName,
            @RequestParam(value = "amount") Integer amount,
            @RequestParam(value = "trans_ref_no") String transRefNo,
            @RequestParam(value = "message") String message
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
        return "redirect:/";
    }

}

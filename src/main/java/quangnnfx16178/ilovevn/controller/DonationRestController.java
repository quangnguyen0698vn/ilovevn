package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.service.DonationService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping({"/api/"})
public class DonationRestController {

    private final DonationService donationService;

    @GetMapping("/donations/size")
    Integer numberOfDonations() {
        return  donationService.countAllByState(StateType.ACCEPTED);
    }

    @GetMapping("/donations/totalRaisedAmount")
    Long totalRaisedAmount() {
        return donationService.totalRaisedAmount(StateType.ACCEPTED);
    }
}

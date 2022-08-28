package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quangnnfx16178.ilovevn.entity.Donation;
import quangnnfx16178.ilovevn.entity.DonationDTO;
import quangnnfx16178.ilovevn.entity.StateType;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.service.DonationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class DonationRestController {

    private final DonationService donationService;

    @GetMapping("/api/donations/size")
    Integer numberOfDonations() {
        return  donationService.countAllByState(StateType.ACCEPTED);
    }

    @GetMapping("/api/donations/totalRaisedAmount")
    Long totalRaisedAmount() {
        return donationService.totalRaisedAmount(StateType.ACCEPTED);
    }

    @GetMapping("/admin/api/donations")
    public ResponseEntity<Map<String, Object>> getAllDonations(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sortField", defaultValue = "id") String sortField,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(name = "keyword", required = false) String keyword
    )
    {
        try {
            log.info("pageNum: " + pageNum);
            log.info("pageSize: " + pageSize);
            log.info("sortField: " + sortField);
            log.info("sortDir: " + sortDir);
            log.info("keyword: " + keyword);

            if (pageSize == -1)
                pageSize = donationService.countAll();
            Page<Donation> page = donationService.listByPage(pageNum, pageSize, sortField, sortDir, keyword);
            Map<String, Object> response = new HashMap<>();
            response.put("pageNum", pageNum);
            response.put("pageSize", page.getSize());
            response.put("totalPages", page.getTotalPages());
            List<DonationDTO> listDonations = new ArrayList<>();
            for (Donation donation : page.getContent()) {
                listDonations.add(new DonationDTO(donation));
            }
            response.put("totalDonations", page.getTotalElements());
            response.put("data", listDonations);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

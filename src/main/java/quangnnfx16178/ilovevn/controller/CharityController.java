package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.entity.Project;
import quangnnfx16178.ilovevn.exception.CharityNotFoundException;
import quangnnfx16178.ilovevn.exception.CharityNotHavingDetailsException;
import quangnnfx16178.ilovevn.exception.ProjectNotFoundException;
import quangnnfx16178.ilovevn.service.CharityService;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CharityController {

    private final CharityService charityService;

    @GetMapping("/view/charity")
    public String viewCharity(@RequestParam("id")Integer id, Model model) {
        try {
            Charity charity = charityService.getCharityById(id);
            if (charity.getHavingDetails() == false)
                throw new CharityNotHavingDetailsException("Đơn vị mã số " + id + " chưa có bài viết giới thiệu đầy đủ");
            model.addAttribute("charity", charity);
        } catch (CharityNotFoundException | CharityNotHavingDetailsException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
            log.error(e.getMessage());
        }
        return "charity_detail";
    }

}

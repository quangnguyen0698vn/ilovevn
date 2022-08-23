package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import quangnnfx16178.ilovevn.entity.Charity;
import quangnnfx16178.ilovevn.service.CharityService;

@RestController
@RequestMapping({"/api/"})
@Log4j2
@RequiredArgsConstructor
public class CharityRestController {
    private final CharityService charityService;

    @RequestMapping(value = "/charities", method = RequestMethod.GET)
    Iterable<Charity> getCharities() {
        Iterable<Charity> charities = charityService.listAll();
        return charities;
    }

    @GetMapping(value = "/charities/page/{pageNum}")
    Iterable<Charity> listPage(@PathVariable("pageNum") Integer pageNum) {
        Iterable<Charity> charities = charityService.listAll(pageNum);
        return charities;
    }

    @GetMapping(value = "/charities/size")
    Integer numberOfCharities() { return charityService.countAll(); }
}

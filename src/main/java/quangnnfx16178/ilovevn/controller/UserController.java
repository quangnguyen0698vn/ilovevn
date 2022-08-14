package quangnnfx16178.ilovevn.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.service.UserService;

import java.util.List;

@Controller
@RequestMapping({"/admin/users", "/admin/users/"})
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String listFirstPage(Model model) {
        return listByPage(1, UserService.DEFAULT_ITEMS_PER_PAGE,model, "id", "asc", null);
    }

    private String listByPage(@RequestParam("pageNum") int pageNum,
                              @RequestParam("pageSize") int pageSize,
                              Model model,
                              @RequestParam("sortField") String sortField ,
                              @RequestParam("sortDir") String sortDir,
                              @RequestParam(value = "keyword", required = false) String keyword)
    {
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);
        return "admin/users";
    }
}

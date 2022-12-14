package quangnnfx16178.ilovevn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import quangnnfx16178.ilovevn.entity.User;
import quangnnfx16178.ilovevn.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/api/users/check_unique_email")
    public String checkDuplicateEmail(String email) {
        return userService.isEmailUnique(email) ? "OK" : "Duplicated";
    }
    @GetMapping("/admin/api/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
        @RequestParam(name = "sortField", defaultValue = "id") String sortField,
        @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
        @RequestParam(name = "keyword", required = false) String keyword
    )
    {
        try {
//            log.info("pageNum: " + pageNum);
//            log.info("pageSize: " + pageSize);
//            log.info("sortField: " + sortField);
//            log.info("sortDir: " + sortDir);
//            log.info("keyword: " + keyword);

            if (pageSize == -1)
                pageSize = userService.countAll();
            Page<User> page = userService.listByPage(pageNum, pageSize, sortField, sortDir, keyword);
            Map<String, Object> response = new HashMap<>();
            response.put("pageNum", pageNum);
            response.put("pageSize", page.getSize());
            response.put("totalPages", page.getTotalPages());
            response.put("totalUsers", page.getTotalElements());
            response.put("data", page.getContent());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



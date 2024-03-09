package app.carstore.web;

import app.carstore.model.view.UserDisplayView;
import app.carstore.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/users")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/view")
    public String adminView(Model model,
                            @PageableDefault(
                                    sort = "id",
                                    direction = Sort.Direction.ASC,
                                    size = 4
                            ) Pageable pageable) {


        Page<UserDisplayView> users = userService.findAllUsers(pageable);

        model.addAttribute("users", users);


        return "admin-view";

    }

}

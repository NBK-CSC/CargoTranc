package com.example.demo.Controllers;

import com.example.demo.Model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ErrorController {

    @GetMapping("/error")
    public String getErrorPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "error";
    }
}

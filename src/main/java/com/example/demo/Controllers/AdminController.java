package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import com.example.demo.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/main")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        return adminService.getAdminPage(user, model);
    }

    @GetMapping("/searchByUsername")
    public String searchForUsername(@AuthenticationPrincipal User user, @RequestParam("username") String username, Model model) {
        adminService.checkUserActivationCode(user, model);
        return adminService.searchUsers(username, model);
    }

    @GetMapping("/searchEmployee")
    public String searchEmployee(@AuthenticationPrincipal User user, @RequestParam("employee") String employee, Model model) {
        adminService.checkUserActivationCode(user, model);
        return adminService.checkEmployees(employee, model);
    }

    @GetMapping("addNewEmployeeOrCar")
    public String getPageAddNewEmployeeOrCar(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("car-form", new Truck());
        model.addAttribute("employee-form", new Employee());
        return "newEmployeeOrCar";
    }

    @PostMapping("/addCar")
    public String addNewCar(@ModelAttribute("car-form") Truck truck,
                            @RequestParam("truckDescription") String truckDescription,
                            @AuthenticationPrincipal User user,
                            BindingResult bindingResult,
                            Model model) {
        truck.setDescription(truckDescription);
        return adminService.addingCar(truck, user, bindingResult, model);
    }

    @PostMapping("/addEmployee")
    public String addNewEmployee(@ModelAttribute("employee-form") Employee employee,
                                 @AuthenticationPrincipal User user,
                                 BindingResult bindingResult,
                                 Model model) {
        return adminService.addingEmployee(employee, user, bindingResult, model);
    }
}

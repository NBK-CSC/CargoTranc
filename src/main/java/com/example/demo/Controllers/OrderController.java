package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import com.example.demo.Services.EmployeeService;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.TruckService;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TruckService truckService;

    @Autowired
    private OrderValidator orderValidator;

    @GetMapping("/makeOrder")
    public String makeOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("orderForm", new Order());
        return "makeOrder";
    }

    @PostMapping("/makeOrderAction")
    public String makeOrderAction(@ModelAttribute Order orderForm,
                                  @RequestParam("truckDescription") String truckDescription,
                                  @RequestParam("numberOfWorkers") int numberOfWorkers,
                                  @AuthenticationPrincipal User user,
                                  BindingResult bindingResult, Model model) {
        List<Employee> workers = employeeService.findAll();
        List<Employee> workersBuf = employeeService.setWorkersToOrder(orderForm, workers);
        List<Truck> trucks = truckService.findAllByDescription(truckDescription);
        Truck truck = truckService.setTruckToOrder(orderForm, trucks);
        if (orderService.validateOrderForm(orderForm, workersBuf, numberOfWorkers, truck, bindingResult, model)) {
            orderService.pasteOrderForm(orderForm, numberOfWorkers, model);
            model.addAttribute("user", user);
            model.addAttribute("again", "yes");
            return "makeOrder";
        }
        orderForm.setCustomerUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        orderForm.setCreationDate(new Date());
        orderForm.setTruck(truck);
        orderForm.setWorkers(workersBuf.subList(0, numberOfWorkers));
        orderService.save(orderForm);
        return "redirect:/main";
    }

    @GetMapping("/deleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/main";
    }
}

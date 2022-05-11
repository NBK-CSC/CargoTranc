package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Model.User;
import com.example.demo.Repositories.OrderRepository;
import com.example.demo.Validators.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderValidator orderValidator;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private MailSender mailSender;

    public void save(Order order) {
        User user = authorizationService.findByUsername(order.getCustomerUsername());
        String message = "Здравствуйте, " + order.getCustomerUsername() + ". Вами был оформлен заказ." + '\n' +
                "Информация о заказе: " + '\n' +
                "Пункт отправки: " + order.getAddressFrom() + '\n' +
                "Пункт назначения: " + order.getAddressTo() + '\n' +
                "Дата оформления заказа: " + order.getCreationDate() + '\n' +
                "Дата выполнения заказа: " + order.getTargetDate() + '\n' +
                "Примерная стоимость: " + order.getPrice() + " руб." + '\n' + '\n' +
                "Спасибо, что выбрали нас!";
        mailSender.send(user.getEmail(), "Новый заказ", message);
        orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByCustomerUsername(String customerUsername) {
        return orderRepository.findAllByCustomerUsername(customerUsername);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findAllByOrderByCustomerUsername() {
        return orderRepository.findAllByOrderByCustomerUsername();
    }

    public void pasteOrderForm(Order orderForm, int numberOfWorkers, Model model) {
        model.addAttribute("addressFrom_paste", orderForm.getAddressFrom());
        model.addAttribute("addressTo_paste", orderForm.getAddressTo());
        model.addAttribute("distance_paste", orderForm.getDistance());
        model.addAttribute("duration_paste", orderForm.getDuration());
        model.addAttribute("targetDate_paste", orderForm.getTargetDate());
        model.addAttribute("hours_paste", orderForm.getTargetTime().split(":")[0]);
        model.addAttribute("minutes_paste", orderForm.getTargetTime().split(":")[1]);
        model.addAttribute("targetTime_paste", orderForm.getTargetTime());
        model.addAttribute("numberOfWorkers_paste", numberOfWorkers);
        model.addAttribute("price_paste", orderForm.getPrice());
    }

    public boolean validateOrderForm(Order orderForm, List<Employee> workersBuf, int numberOfWorkers, Truck truck, BindingResult bindingResult, Model model) {
        orderValidator.customValidate(orderForm, numberOfWorkers, workersBuf, truck, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError)object;
                    model.addAttribute(fieldError.getField(), fieldError.getCode());
                }
            }

            return true;
        }
        else
            return false;
    }

    public List<Order> searchOrdersByUsername(String username) {
        return orderRepository.findAllByCustomerUsernameContainingIgnoreCase(username);
    }
}

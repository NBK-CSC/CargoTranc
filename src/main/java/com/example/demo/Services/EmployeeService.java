package com.example.demo.Services;

import com.example.demo.Model.Employee;
import com.example.demo.Model.Order;
import com.example.demo.Repositories.EmployeeRepository;
import com.example.demo.Validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeValidator employeeValidator;
    
    @Transactional
    public void deleteAllByName(String name) {
        employeeRepository.deleteAllByName(name);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> searchEmployee(String employee) {
        return employeeRepository.findByNameContainingIgnoreCase(employee);
    }

    public List<Order> getAllEmployeesOrders() {
        List<Order> orders = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            orders.addAll(employee.getOrders());
        }
        return orders;
    }

    public List<Employee> setWorkersToOrder(Order orderForm, List<Employee> workers) {
        List<Employee> workersBuf = new ArrayList<>();
        boolean flag;
        for (Employee worker : workers) {
            flag = true;
            if (worker.getOrders().isEmpty())
                workersBuf.add(worker);
            else {
                for (Order order : worker.getOrders()) {
                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
                        flag = false;
                }
                if (flag)
                    workersBuf.add(worker);
            }
        }
        return workersBuf;
    }

    public boolean validateEmployee(Employee employee, BindingResult bindingResult, Model model) {
        employeeValidator.validate(employee, bindingResult);
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
}

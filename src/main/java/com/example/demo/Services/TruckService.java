package com.example.demo.Services;

import com.example.demo.Model.Order;
import com.example.demo.Model.Truck;
import com.example.demo.Repositories.TruckRepository;
import com.example.demo.Validators.TruckValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckValidator truckValidator;
    
    public List<Truck> getAllTrucks() {
        return (List<Truck>) truckRepository.findAll();
    }

    @Transactional
    public void deleteAllByDescription(String description) {
        truckRepository.deleteAllByDescription(description);
    }

    @Transactional
    public void deleteAllByCarNumber(String carNumber) {
        truckRepository.deleteAllByCarNumber(carNumber);
    }

    public void save(Truck truck) {
        truckRepository.save(truck);
    }

    public List<Truck> findAllByDescription(String description) {
        return truckRepository.findAllByDescription(description);
    }

    public Truck findByCarNumber(String carNumber) {
        return truckRepository.findByCarNumber(carNumber);
    }

    public Truck setTruckToOrder(Order orderForm, List<Truck> trucks) {
        boolean flag;
        for (Truck truck : trucks) {
            flag = true;
            if (truck.getOrders().isEmpty()) {
                return truck;
            }
            else {
                for (Order order : truck.getOrders()) {
                    if (order.getTargetDate().equals(orderForm.getTargetDate()))
                        flag = false;
                }
                if (flag) {
                    return truck;
                }
            }
        }
        return null;
    }

    public boolean validateTruck(Truck truck, BindingResult bindingResult, Model model) {
        truckValidator.validate(truck, bindingResult);
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

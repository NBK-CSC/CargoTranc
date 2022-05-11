package com.example.demo.Validators;

import com.example.demo.Model.Truck;
import com.example.demo.Services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TruckValidator implements Validator {

    @Autowired
    private TruckService truckService;

    private Pattern pattern;
    private Matcher matcher;

    private static final String CARNUMBER_PATTERN = "[А-Я]\\d{3}[A-Я]{2}\\d{2,3}";

    @Override
    public boolean supports(Class<?> aClass) {
        return Truck.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Truck truck = (Truck)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carNumber", "обязательно к заполнению");
        if (!validateCarNumber(truck.getCarNumber()))
            errors.rejectValue("carNumber", "неправильный формат автомобильного номера");
        if (truckService.findByCarNumber(truck.getCarNumber()) != null)
            errors.rejectValue("carNumber", "номер уже занят");
    }

    public boolean validateCarNumber(String carNumber) {
        pattern = Pattern.compile(CARNUMBER_PATTERN);
        matcher = pattern.matcher(carNumber);
        return matcher.matches();
    }
}

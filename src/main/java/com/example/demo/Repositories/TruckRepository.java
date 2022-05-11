package com.example.demo.Repositories;

import com.example.demo.Model.Truck;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TruckRepository extends CrudRepository<Truck, Long> {

    List<Truck> findAllByDescription(String description);
    Truck findByCarNumber(String carNumber);
    
    void deleteAllByDescription(String description);

    void deleteAllByCarNumber(String carNumber);
}

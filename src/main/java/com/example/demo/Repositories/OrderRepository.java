package com.example.demo.Repositories;

import com.example.demo.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerUsername(String customerUsername);

    void deleteById(Long id);

    List<Order> findAllByOrderByCustomerUsername();

    List<Order> findAllByCustomerUsernameContainingIgnoreCase(String username);
}

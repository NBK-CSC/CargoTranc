package com.example.demo.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "truck8")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "car_number")
    private String carNumber;

    @OneToMany(mappedBy = "truck",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<Order> orders;

    public Truck() {
    }

    public Truck(String description, String carNumber, List<Order> orders) {
        this.description = description;
        this.carNumber = carNumber;
        this.orders = orders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        if (orders != null) {
            orders.forEach(a->{
                a.setTruck(this);
            });
        }
        this.orders = orders;
    }
}

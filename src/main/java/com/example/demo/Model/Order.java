package com.example.demo.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "public", name = "order8")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_username")
    private String customerUsername;

    @Column(name = "address_from")
    private String addressFrom;

    @Column(name = "address_to")
    private String addressTo;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "target_date")
    private String targetDate;

    @Column(name = "targetTime")
    private String targetTime;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "employees_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> workers;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @Column(name = "price")
    private int price;
    public Order() {
    }

    public Order(String customerUsername,
                 String addressFrom,
                 String addressTo,
                 String targetDate,
                 String targetTime,
                 Date creationDate,
                 String distance,
                 String duration,
                 List<Employee> workers,
                 Truck truck,
                 int price) {
        this.customerUsername = customerUsername;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.targetDate = targetDate;
        this.targetTime = targetTime;
        this.creationDate = creationDate;
        this.distance = distance;
        this.duration = duration;
        this.workers = workers;
        this.truck = truck;
        this.price = price;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCustomerUsername() {
        return customerUsername;
    }
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }
    public String getAddressFrom() {
        return addressFrom;
    }
    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }
    public String getAddressTo() {
        return addressTo;
    }
    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }
    public String getTargetDate() {
        return targetDate;
    }
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
    public String getTargetTime() {
        return targetTime;
    }
    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public List<Employee> getWorkers() {
        return workers;
    }
    public void setWorkers(List<Employee> workers) {
        this.workers = workers;
    }
    public Truck getTruck() {
        return truck;
    }
    public void setTruck(Truck truck) {
        this.truck = truck;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}

package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id", unique = true, nullable = false)
    private int id;

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Column(name = "price", nullable = false)
    private Double price;

    @Getter
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrderList = new ArrayList<>();

    public Product(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product() {

    }

    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product merge(Product target) {
        if (name != null && !name.equals(target.name)) target.name = name;
        if (price != null && !price.equals(target.price)) target.price = price;
        if (!description.equals(target.description)) target.description = description;
        return target;
    }

}

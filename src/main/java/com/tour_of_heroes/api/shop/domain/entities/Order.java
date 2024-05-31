package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Getter
    @Id
    @Column(name = "order_id")
    private UUID id;

    @Getter
    @Column(name = "expedition_date")
    private Timestamp expeditionDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrderList = new ArrayList<>();

    public Order() {
        id = UUID.randomUUID();
        expeditionDate = new Timestamp(System.currentTimeMillis());
    }

    public List<Product> getProductOrderList() {
        return productOrderList.stream().map(ProductOrder::getProduct).toList();
    }

    public Order merge(Order target) {
        return target;
    }

    public void addProduct(Product product) {
        ProductOrder productOrder = new ProductOrder(this, product);
        productOrderList.add(productOrder);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", expeditionDate=" + expeditionDate +
                ", productOrderList=" + getProductOrderList().stream().map(Product::toString).toString() +
                '}';
    }
}

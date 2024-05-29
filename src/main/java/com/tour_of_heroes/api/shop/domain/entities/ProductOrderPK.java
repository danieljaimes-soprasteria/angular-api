package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProductOrderPK implements Serializable {

    @Column(name = "product_id", insertable = false, updatable = false, unique = true, nullable = false)
    private int productId;

    @Column(name = "order_id", insertable = false, updatable = false, unique = true, nullable = false)
    private UUID orderId;


}

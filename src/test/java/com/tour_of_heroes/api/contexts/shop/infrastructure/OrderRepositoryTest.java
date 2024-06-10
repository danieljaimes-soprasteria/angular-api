package com.tour_of_heroes.api.contexts.shop.infrastructure;

import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.OrderRepository;
import com.tour_of_heroes.api.shop.domain.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    private Order order;

    @BeforeEach
    public void setupTestData(){
        // Given : Setup object or precondition
        order = new Order();
    }
}

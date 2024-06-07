package com.tour_of_heroes.api.apps.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour_of_heroes.api.controllers.ProductController;
import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.shop.domain.contracts.services.ProductService;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.infrastructure.dto.InProductDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService srv;

    private String urlTemplate;

    @BeforeEach
    void setUp() {
        urlTemplate = "/api/shop/products";
    }

    @Test
    void whenGetOneProduct_thenControlCorrectFlow() throws Exception {
        int id = 1;
        Product product = new Product(id, "Axe", 100, "Deodorant");
        when(srv.getOne(anyInt())).thenReturn(Optional.of(product));

        mockMvc.perform(get(urlTemplate + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andDo(print());

        verify(srv, Mockito.times(1)).getOne(anyInt());
    }

    @Test
    void whenCreateProduct_thenControlCorrectFlow() throws Exception {
        int id = 1;
        InProductDTO inProductDTO = new InProductDTO("Axe", 100.0, "Deodorant");
        Product product = InProductDTO.from(id, inProductDTO);
        OutProductDTO outProductDTO = OutProductDTO.from(product);

        when(srv.add(any(Product.class))).thenReturn(product);
        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inProductDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(outProductDTO.getId()))
                .andExpect(jsonPath("$.name").value(outProductDTO.getName()))
                .andExpect(jsonPath("$.price").value(outProductDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(outProductDTO.getDescription()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .add(Mockito.any(Product.class));
    }

    @Test
    void whenGetAllProducts_thenControlCorrectFlow() throws Exception {
        List<Product> productList = List.of(
                new Product(1, "Axe1", 100.0, "Deodorant"),
                new Product(2, "Axe2", 130.0, "Deodorant"),
                new Product(3, "Axe3", 150.0, "Deodorant")
        );
        when(srv.getAll()).thenReturn(productList);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(productList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(productList.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(productList.get(0).getDescription()))
                .andExpect(jsonPath("$[0].price").value(productList.get(0).getPrice()))
                .andDo(print());

        verify(srv, Mockito.times(1)).getAll();
    }

    @Test
    void whenUpdateProduct_thenControlCorrectFlow() throws Exception {
        int id = 1;
        InProductDTO inProductDTO = new InProductDTO("Axe", 100.0, "Deodorant");
        Product product = InProductDTO.from(id, inProductDTO);
        OutProductDTO outProductDTO = OutProductDTO.from(product);

        when(srv.modify(any(Product.class))).thenReturn(product);

        mockMvc.perform(patch(urlTemplate + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(outProductDTO.getId()))
                .andExpect(jsonPath("$.name").value(outProductDTO.getName()))
                .andExpect(jsonPath("$.price").value(outProductDTO.getPrice()))
                .andExpect(jsonPath("$.description").value(outProductDTO.getDescription()))
                .andDo(print());

        verify(srv, Mockito.times(1))
                .modify(Mockito.any(Product.class));
    }

    @Test
    void whenDeleteProduct_thenControlCorrectFlow() throws Exception {
        int id = 1;
        Product product = new Product(id, "Axe", 100.0, "Deodorant");

        when(srv.getOne(id)).thenReturn(Optional.of(product));

        doNothing().when(srv).deleteById(id);

        mockMvc.perform(delete(urlTemplate + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(srv, Mockito.times(1)).getOne(id);
        verify(srv, Mockito.times(1)).deleteById(id);
    }
}

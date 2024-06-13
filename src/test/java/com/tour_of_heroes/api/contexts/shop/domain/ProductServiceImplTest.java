package com.tour_of_heroes.api.contexts.shop.domain;

import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.repositories.ProductRepository;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.domain.services.ProductServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productServiceMock;

    @Mock
    private ProductRepository repository;

    @Nested
    class OK {
        @Test
        void whenGetAll_thenReturnProductList() {
            List<Product> productListMock = List.of(new Product("iPhone 1", 1000, "Smartphone"), new Product("iPhone 2", 1250, "Smartphone"), new Product("iPhone 3", 1500, "Smartphone"));
            when(repository.findAll()).thenReturn(productListMock);
            List<Product> productListService = productServiceMock.getAll();
            assertThat(productListService, is(productListMock));
        }

        @Test
        void whenGetOneById_thenReturnProduct() {
            Product productMockExpected = new Product("iPhone 1", 1000, "Smartphone");
            when(repository.findById(productMockExpected.getId())).thenReturn(Optional.of(productMockExpected));
            Optional<Product> productActual = productServiceMock.getOne(productMockExpected.getId());
            assertSame(productMockExpected, productActual.get());
        }

        @Test
        void whenAddProductSaveProduct_thenReturnProduct() throws InvalidDataException {
            Product productMock = new Product("iPhone 1", 1000, "Smartphone");
            when(repository.save(productMock)).thenReturn(productMock);
            when(productServiceMock.add(productMock)).thenReturn(productMock);
            assertEquals(productServiceMock.add(productMock), repository.save(productMock));
        }

        @Test
        void whenModifyOrder_thenReturnOrder() throws NotFoundException, InvalidDataException {

            Product productOne = new Product(1, "Product1", 150.0, "Product1 Description");
            Product productTwo = new Product(2, "Product2", 200.0, "Product2 Description");

            when(repository.findById(productOne.getId())).thenReturn(Optional.of(productOne));
            lenient().when(repository.save(productOne.merge(productTwo))).thenReturn(productOne);
            lenient().when(productServiceMock.modify(productOne)).thenReturn(productTwo);
            assertEquals(productServiceMock.modify(productOne), productTwo);
        }

        @Test
        void deleteProductWhenProductNotNull() throws InvalidDataException {

            Product product = new Product();
            doNothing().when(repository).delete(product);
            productServiceMock.delete(product);
            verify(repository, times(1)).delete(product);
        }

        @Test
        void deleteProductById() {
            int id = 1;
            doNothing().when(repository).deleteById(id);
            productServiceMock.deleteById(id);
            verify(repository, times(1)).deleteById(id);
        }
    }

    @Nested
    class KO {
        @Test
        void whenAddNullProduct_thenThrowInvalidDataException() {

            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.add(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyNullProduct_thenThrowInvalidDataException() {

            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.modify(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyProductNotSaved_thenThrowNotFoundException() {

            Product product = new Product();
            NotFoundException exception = assertThrows(NotFoundException.class, () -> productServiceMock.modify(product));
            assertEquals(exception.getMessage(), NotFoundException.MESSAGE_STRING);
        }

        @Test
        void whenDeleteNullProduct_thenThrowInvalidDataException() {
            Product product = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> productServiceMock.delete(product));
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }
    }
}

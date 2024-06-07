package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.shared.exceptions.BadRequestException;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import com.tour_of_heroes.api.shop.domain.contracts.services.ProductService;
import com.tour_of_heroes.api.shop.domain.entities.Product;
import com.tour_of_heroes.api.shop.infrastructure.dto.InProductDTO;
import com.tour_of_heroes.api.shop.infrastructure.dto.OutProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shop/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public OutProductDTO getProduct(@PathVariable int id) throws NotFoundException {
        Optional<Product> product = productService.getOne(id);
        if (product.isEmpty()) throw new NotFoundException();
        return OutProductDTO.from(product.get());
    }

    @GetMapping
    public List<OutProductDTO> getAllProducts() {
        List<Product> productList = productService.getAll();
        List<OutProductDTO> outProductDTOList = new ArrayList<>(productList.stream().map(OutProductDTO::from).toList());
        return outProductDTOList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public OutProductDTO createProduct(@Valid @RequestBody InProductDTO inProductDTO) throws InvalidDataException {
        return OutProductDTO.from(productService.add(InProductDTO.from(inProductDTO)));
    }

    @PatchMapping("/{id}")
    @Transactional
    public OutProductDTO updateProduct(@PathVariable int id, @Valid @RequestBody InProductDTO inProductDTO) throws NotFoundException, BadRequestException, InvalidDataException {
        return OutProductDTO.from(productService.modify(InProductDTO.from(id, inProductDTO))
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) throws NotFoundException {
        if(productService.getOne(id).isEmpty()) throw new NotFoundException();
        productService.deleteById(id);
    }
}

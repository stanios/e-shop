package com.finalproject.cf.controller;

import com.finalproject.cf.dto.ProductDTO;
import com.finalproject.cf.entity.ImageModel;
import com.finalproject.cf.entity.Product;
import com.finalproject.cf.exceptions.ConvertingImgException;
import com.finalproject.cf.exceptions.EntityNotFoundException;
import com.finalproject.cf.exceptions.ErrorDetails;
import com.finalproject.cf.exceptions.NoProductsFoundException;
import com.finalproject.cf.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(summary = "Add a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product saved successfully",
                content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Failed to convert img to file",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "400", description = "Validation Error",
                content = @Content)
    })
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDTO>  addNewProduct(@Valid @RequestPart("product") ProductDTO productDTO,
                                 @RequestPart("imageFile") MultipartFile[] files) throws ConvertingImgException {
        Set<ImageModel> imageModels = uploadImage(files);
        productDTO.setProductImages(imageModels);
        Product product = productService.insertNewProduct(productDTO);
        ProductDTO dto = map(product);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Gets all the products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned all the products successfully",
                content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "No Products available",
                    content = @Content)
    })
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = {"/getAllProducts"})
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws NoProductsFoundException {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> dtos = map(products);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Operation(summary = "Delete a product by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a product",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product was not found",
                    content = @Content)
    })
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = {"/deleteProductDetails/{productId}"})
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long id) throws EntityNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get a product by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return product by Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Product was not found",
                    content = @Content)
    })
    @PreAuthorize("hasRole('Admin')")
    @GetMapping({"/getProductDetails/{productId}"})
    public ResponseEntity<ProductDTO> getProductDetails(@PathVariable("productId") Long id) throws EntityNotFoundException {
        Product product = productService.getProductDetails(id);
        ProductDTO dto = map(product);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws ConvertingImgException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
        try {
                ImageModel imageModel = new ImageModel(
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getBytes()
                );
                imageModels.add(imageModel);
            } catch(IOException e){
                throw new ConvertingImgException("Failed to read bytes from file: " + file.getOriginalFilename());
            }
        }

        return imageModels;
    }

    private ProductDTO map(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductDiscountedPrice(),
                product.getProductActualPrice(),
                product.getProductImages()
        );
    }


    private List<ProductDTO> map(List<Product> products) {
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product pro : products) {
            ProductDTO productDTO = new ProductDTO(
                    pro.getProductId(),
                    pro.getProductName(),
                    pro.getProductDescription(),
                    pro.getProductDiscountedPrice(),
                    pro.getProductActualPrice(),
                    pro.getProductImages()
            );
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}

package com.finalproject.cf.dto;

import com.finalproject.cf.entity.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;

    @Size(min = 3, message = "Username must have at least ${min} characters")
    private String productName;

    private String productDescription;

    private Double productDiscountedPrice;
    @NotNull
    private Double productActualPrice;
    private Set<ImageModel> productImages;
}

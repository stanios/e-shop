package com.finalproject.cf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_IMAGES",
            joinColumns = {
                @JoinColumn(name = "PRODUCT_ID"),
            },
            inverseJoinColumns = {
                @JoinColumn(name = "IMAGE_ID")
            }
    )
    private Set<ImageModel> productImages;
}

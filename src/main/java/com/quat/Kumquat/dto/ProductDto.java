package com.quat.Kumquat.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Product should have a name.")
    private String itemName;
    @NotNull(message = "Product should have a price.")
    @Min(value = 0)
    private double price;
    @NotNull(message = "Product should have a stock number.")
    @Min(value = 0)
    private int stock;
    private String description;
    private byte[] image;
}
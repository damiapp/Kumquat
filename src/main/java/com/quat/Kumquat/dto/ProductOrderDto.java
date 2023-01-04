package com.quat.Kumquat.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.quat.Kumquat.model.ProductOrder} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto{
    @NotNull
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long productsId;
}
package com.BikkadIT.ElectronicStroe.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private ProductDto productDto;

    private int quantity;

    private int totalPrice;


}

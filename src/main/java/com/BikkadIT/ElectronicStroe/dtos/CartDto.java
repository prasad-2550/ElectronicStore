package com.BikkadIT.ElectronicStroe.dtos;

import com.BikkadIT.ElectronicStroe.model.CartItem;
import com.BikkadIT.ElectronicStroe.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;

    private Date createdAt;

    private UserDto userDto;

    //mapping cart item
    private List<CartItemDto> items =new ArrayList<>();



}

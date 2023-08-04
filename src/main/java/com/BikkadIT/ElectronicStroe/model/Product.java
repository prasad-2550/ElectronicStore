package com.BikkadIT.ElectronicStroe.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

     @Id
     private String productId;

     private String title;

     @Column(length = 500)
     private String description;

     private int price;

     private int discountPrice;

     private int quantity;

     private Date addedDate;

     private boolean live;

     private boolean stock;

     private String productImage;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "category_id")
     private  Category category;

}

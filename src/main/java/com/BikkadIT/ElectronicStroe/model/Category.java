package com.BikkadIT.ElectronicStroe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorires")
public class Category {

    @Id
    private String categoryId;

    @Column(name = "category_title",length = 25,nullable = false)
    private String title;

    @Column(name = "category_desc",length = 25)
    private String description;

    private String coverImage;

    @OneToMany( mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products= new ArrayList<>();
}

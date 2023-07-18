package com.BikkadIT.ElectronicStroe.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {


    private String categoryId;

    private String title;

    private String description;

    private String categoryImage;
}

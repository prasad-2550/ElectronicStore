package com.BikkadIT.ElectronicStroe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4,message = "!!Category Title must be of 4 Characters !!")
    private String title;

    @NotBlank(message = "!!Description required!!")
    private String description;

    @NotBlank(message = "Cover Image Required!!")
    private String coverImage;
}

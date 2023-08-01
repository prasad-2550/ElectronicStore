package com.BikkadIT.ElectronicStroe.services;

import com.BikkadIT.ElectronicStroe.dtos.CategoryDto;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import org.springframework.stereotype.Service;


public interface CategoryService {

    // create
    CategoryDto create(CategoryDto categoryDto);

    // update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    // delete
    void  delete(String categoryId);
    //get single category
    CategoryDto get(String categoryId);

    //get all category
    PageableResponse<CategoryDto> getAll(int pageSize,int pageNumber,String sortBy,String sortDir);


    //search category


}

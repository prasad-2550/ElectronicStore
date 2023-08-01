package com.BikkadIT.ElectronicStroe.services;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.ProductDto;

public interface ProductService {
    //CREATE
    ProductDto create(ProductDto productDto);


    // UPDATE
    ProductDto update(ProductDto productDto, String productId);


    // DELETE
    void delete(String productId);

    // GET SINGLE
    ProductDto getSingle(String productId);


    // GET ALL
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    // GET ALL LIVE
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    // SEARCH
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    // create Product With Category
    ProductDto createWithCategory(ProductDto  productDto,String categoryId);

    //update category with product
    ProductDto updateCategory(String productId,String categoryId);

    // get all of category
    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sorDir);
}

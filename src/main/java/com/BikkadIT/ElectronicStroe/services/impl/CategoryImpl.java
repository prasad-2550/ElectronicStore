package com.BikkadIT.ElectronicStroe.services.impl;

import com.BikkadIT.ElectronicStroe.dtos.CategoryDto;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.exception.ResourceNotFoundException;
import com.BikkadIT.ElectronicStroe.helper.Helper;
import com.BikkadIT.ElectronicStroe.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.BikkadIT.ElectronicStroe.repository.CategoryRepository;
import com.BikkadIT.ElectronicStroe.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoryImpl.class);



    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Inside createCategory()");
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        logger.info("Generated unique Id: "+ categoryId);
        Category category= modelMapper.map(categoryDto, Category.class);
      Category savedCategory=categoryRepository.save(category);
        logger.info("saved Category is "+ savedCategory);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        logger.info("Inside updateCategory()"+categoryId);
        //get category of given id
       Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(" categoryId"));
       //update category
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
         Category updatedCategory =categoryRepository.save(category);
        logger.info("Category updated successfully "+updatedCategory);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        logger.info("Inside deleteCategory()"+categoryId);
        //get category of given id
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(" categoryId"));
        categoryRepository.delete(category);
        logger.info("Category deleted successfully"+category);

    }

    @Override
    public CategoryDto get(String categoryId) {
        logger.info("Inside getCategory()"+categoryId);
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(" categoryId"));
        logger.info("Category with Id"+category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Inside GetAll()");
        Sort sort =(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        logger.info("List of All Category "+pageableResponse);
        return pageableResponse;
    }

    }


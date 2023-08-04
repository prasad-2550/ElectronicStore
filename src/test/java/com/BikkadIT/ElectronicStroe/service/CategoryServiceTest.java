package com.BikkadIT.ElectronicStroe.service;

import com.BikkadIT.ElectronicStroe.dtos.CategoryDto;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.model.Category;
import com.BikkadIT.ElectronicStroe.repository.CategoryRepository;
import com.BikkadIT.ElectronicStroe.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest   {

    @Autowired
    CategoryService categoryService;
    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    Category category;

    @BeforeEach
    public void init(){
        category= Category.builder().description("this is description Test for category")
                .coverImage("testCategory.png").title("Test Title")
                .build();
    }
    // CreateCategory
    @Test
    public void createCategoryTest(){
      Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto categoryDto = categoryService.create(modelMapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals("Test Title",categoryDto.getTitle());
    }
    // UpdateCategory
    @Test
    public void updateCategoryTest(){
        String categoryId="";

        CategoryDto categoryDto = CategoryDto.builder().description("Test Description2").coverImage("test2.png").title("Test2").build();
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto updated = categoryService.update(categoryDto,categoryId);

        System.out.println(updated.getTitle());

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Test2",updated.getTitle());
    }
    //deleteCategory
    @Test
    public void deleteCategoryTest(){
        String categoryId="";
        Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        categoryService.delete(categoryId);

        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }
    // getAll
    @Test
    public void getAllTest(){

       Category category1= Category.builder().description("Mobile description")
                .coverImage("15.png").title("Iphone 15")
                .build();

       Category category2= Category.builder().description("laptop description")
                .coverImage("laptop.png").title("laptop yoga")
                .build();

       Category category3= Category.builder().description("cooler description")
                .coverImage("154.png").title("cooler 15")
                .build();


        Category category4= Category.builder().description("washing machine description")
                .coverImage("15.png").title("washing machine")
                .build();

        List<Category> categoryList = Arrays.asList(category1,category2,category3,category4);
        Page<Category> categoryPage =new PageImpl<>(categoryList);

        Mockito.when(categoryRepository.findAll((Pageable)Mockito.any())).thenReturn(categoryPage);
        PageableResponse<CategoryDto> categoryServiceAll= categoryService.getAll(1,1,"title","asc");
        Assertions.assertEquals(4,categoryServiceAll.getContent().size());
    }
    //getSingleCategory
    @Test
    public void getSingleCategoryTest(){
      String  categoryId="";
      Mockito.when(categoryRepository.findById(Mockito.any())).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.get(categoryId);

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle());

    }
}

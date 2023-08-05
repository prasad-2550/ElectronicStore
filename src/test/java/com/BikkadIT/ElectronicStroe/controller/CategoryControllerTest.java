package com.BikkadIT.ElectronicStroe.controller;

import com.BikkadIT.ElectronicStroe.dtos.CategoryDto;
import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.model.Category;
import com.BikkadIT.ElectronicStroe.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    Category category,category1,category2;

    CategoryDto categoryDto;

    @BeforeEach
    public void init(){

        category=Category.builder()
                .categoryId("laptop123").title("laptop").coverImage("dell.png")
                .description("All laptop model available")
                .build();

        category1=Category.builder()
                .categoryId("mob123")
                .categoryId("laptop123").title("laptop").coverImage("dell.png")
                .description("All laptop model available")
                .build();


        category2=Category.builder()
                .categoryId("comp999")
                .categoryId("laptop123").title("laptop").coverImage("dell.png")
                .description("All laptop model available")
                .build();
    }

    @Test
    public void createCategoryTest() throws Exception{

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.create(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId="abc123";
        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.update(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());


    }
    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto object1 = CategoryDto.builder().title("Laptop").description("Laptops are available").coverImage("laptop.jpg").build();
        CategoryDto object2 = CategoryDto.builder().title("Mobile").description("Mobile available").coverImage("mobile.jpg").build();
        CategoryDto object3 = CategoryDto.builder().title("Desktop").description("Desktop are available").coverImage("desktop.jpg").build();
       PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1, object2, object3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(categoryService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
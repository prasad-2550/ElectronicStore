package com.BikkadIT.ElectronicStroe.controller;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.UserDto;
import com.BikkadIT.ElectronicStroe.model.User;
import com.BikkadIT.ElectronicStroe.services.UserService;
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
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {

        user = User.builder().name("prasad").imageName("prasad.png")
                .about("prasad is tester").email("prasad@gmail.com").password("abc123")
                .gender("male").build();

    }

    @Test
    public void createUserTest() throws Exception {
        // user+Post+user data as json
        //data as json+status created
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        // actual req for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").exists());
    }
    @Test
    public void updateUserTest() throws Exception {
       String userId="1234";
        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/user/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());



    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    @Test
    public void getAllUsersTest() throws Exception {

        UserDto object1 = UserDto.builder().name("prasad").email("prasad@gmail.com").password("prasad@23").about("Testing").build();
        UserDto object2 = UserDto.builder().name("ankit").email("anku50@gmail.com").password("zzz").about("Testing").build();
        UserDto object3 = UserDto.builder().name("raju").email("raju@gmail.com").password("aabbcc").about("Testing").build();
        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1, object2, object3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(10000);
        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}

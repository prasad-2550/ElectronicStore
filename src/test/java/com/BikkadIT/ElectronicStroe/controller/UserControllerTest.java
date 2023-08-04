package com.BikkadIT.ElectronicStroe.controller;

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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public  void init(){

        User user1 = User.builder().name("prasad").imageName("prasad.png")
                .about("prasad is tester").email("prasad@gmail.com").password("abc123")
                .gender("male").build();

        User  user2 = User.builder().name("babuRao").imageName("babu.png")
                .about("babu is tester").email("babu@gmail.com").password("abc123")
                .gender("male").build();

        User user3 = User.builder().name("rahul").imageName("rahul.png")
                .about("rahul is tester").email("rahul@gmail.com").password("abc123")
                .gender("male").build();

        User  user4 = User.builder().name("raju").imageName("raju.png")
                .about("raju is tester").email("raju@gmail.com").password("abc123")
                .gender("male").build();

    }
    @Test
    public void createUserTest() throws Exception {
        // user+Post+user data as json
        //data as json+status created
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        // actual req for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name").exists());
    }

    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}

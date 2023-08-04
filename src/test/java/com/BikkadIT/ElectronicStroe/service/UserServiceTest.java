package com.BikkadIT.ElectronicStroe.service;

import com.BikkadIT.ElectronicStroe.dtos.UserDto;
import com.BikkadIT.ElectronicStroe.model.User;
import com.BikkadIT.ElectronicStroe.repository.UserRepository;
import com.BikkadIT.ElectronicStroe.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private ModelMapper  modelMapper;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

     private User user;

    @BeforeEach
    public void init(){

        user = User.builder().name("prasad").imageName("prasad.png")
                .about("prasad is tester").email("prasad@gmail.com").password("abc123")
                .gender("male").build();

    }

    // Create User
    @Test
    public void createUserTest(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("prasad",user1.getName());
    }
    // update User
   @Test
    public void updateUserTest(){
        String userId="";
        UserDto userDto = UserDto.builder().name("prasad pawar").imageName("prasad1.png")
                .about("prasad is not tester").email("prasad1@gmail.com").password("abc1234")
                .gender("male").build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

       UserDto updated = userService.updateUser(userDto, userId);
       System.out.println(updated.getName());
       Assertions.assertNotNull(userDto);
       Assertions.assertEquals("prasad pawar",updated.getName());
   }
   // delete user
    void deleteUserTest(){

    }


}

package com.BikkadIT.ElectronicStroe.service;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
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
    @Test
   public void deleteUserTest(){
        String userId="";
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }
        // getAll
    @Test
    public void getAllUserTest(){
       User user1 = User.builder().name("prasad").imageName("prasad.png")
                .about("prasad is tester").email("prasad@gmail.com").password("abc123")
                .gender("male").build();

       User  user2 = User.builder().name("prasad").imageName("prasad.png")
                .about("prasad is tester").email("prasad@gmail.com").password("abc123")
                .gender("male").build();


        List<User> userList = Arrays.asList(user,user1,user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3,allUser.getContent().size());

    }
    // getSingle
    @Test
    public void getUserById(){
        String userId="";
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        // for call service method
        UserDto userById = userService.getUserById(userId);
        Assertions.assertNotNull(userById);
        Assertions.assertEquals(user.getName(),userById.getName(),"Name not matched");
    }
    // get userByEmail
    @Test
    public void userByEmail(){
        String email="";
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        UserDto userByEmail = userService.getUserByEmail(email);
        Assertions.assertNotNull(email);
        Assertions.assertEquals(user.getEmail(),userByEmail.getEmail(),"email not matched");
    }
    // search user
    @Test
    public  void searchUserTest(){

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


        String keyword="bauRao";
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1,user2,user3,user4));
        List<UserDto> searchedUser = userService.searchUser(keyword);

        Assertions.assertEquals(4,searchedUser.size(),"Size Not Match");


    }
}

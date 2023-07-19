package com.BikkadIT.ElectronicStroe.services.impl;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.exception.ResourceNotFoundException;
import com.BikkadIT.ElectronicStroe.helper.Helper;
import com.BikkadIT.ElectronicStroe.services.UserService;
import com.BikkadIT.ElectronicStroe.dtos.UserDto;
import com.BikkadIT.ElectronicStroe.model.User;
import com.BikkadIT.ElectronicStroe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto createUser(UserDto userDto)
    {
        logger.info("Inside createUser()");
        //generate unique id in String Format
        String userId = UUID.randomUUID().toString();
        logger.info("Generate unique Id: "+ userId);
        userDto.setUserId(userId);

        // dto -> entity
        User user = dtoToEntity(userDto);
        logger.info("Saving the User: "+user);
        User savedUser = userRepository.save(user);
        logger.info("saved User "+savedUser);
        //entity -> dto
        UserDto newDto = entityToDto(savedUser);
        logger.info("Conversion Successful");
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId)
    {
        logger.info("Inside updateUser() with User Id "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With ID"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        logger.info("Updated User: "+user);
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info("User updated successfully "+updatedDto);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId)
    {
        logger.info("Inside deleteUser() with id "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With given Id"));
        //delete user profile Image
        String fullPath = imagePath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch(NoSuchFileException ex){
            logger.info("User Image Not Found In Folder");
            ex.printStackTrace();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        userRepository.delete(user);
        logger.info("Deleted User: "+user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        Sort sort = (sortDir.equalsIgnoreCase("des" ))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //default page no start with zero
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
        logger.info("Inside getAllUser");
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
        logger.info("List Of User: "+response);
        return response;

    }

    @Override
    public UserDto getUserById(String userId)
    {
        logger.info("Inside getUserById() with User Id: "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with Given Id"));
        logger.info("User with Id: "+user);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email)
    {
        logger.info("Inside getUserByEmail() with Email: "+email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given Email Id And Password"));
        logger.info("User By email: "+user);
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword)
    {
        logger.info("Inside searchUser() with keyword: "+keyword);
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info("User details :"+dtoList);
        return dtoList;
    }
    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
        return modelMapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .name(userDto.getName())
//                .userId(userDto.getUserId())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getGender())
//                .gender(userDto.getGender()).build();
        return modelMapper.map(userDto, User.class);
    }
}
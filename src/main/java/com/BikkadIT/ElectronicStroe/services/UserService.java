package com.BikkadIT.ElectronicStroe.services;

import com.BikkadIT.ElectronicStroe.dtos.PageableResponse;
import com.BikkadIT.ElectronicStroe.dtos.UserDto;
import com.BikkadIT.ElectronicStroe.model.User;

import java.util.List;

public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);

    //delete
    void  deleteUser(String userId);

    //get single user
    UserDto getUserById(String userId);


    //getAll user
    PageableResponse<UserDto>  getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto>searchUser(String keyword);




}

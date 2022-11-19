package com.example.managelibrary.service;

import org.springframework.stereotype.Service;

import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.entity.User;

@Service
public interface UserService {
    void save(UserDto userDto);
    Boolean checkPasswordUser(String email,String password);
    Boolean checkUserbyEmail(String email);
    User getUserbyEmail(String email);
}

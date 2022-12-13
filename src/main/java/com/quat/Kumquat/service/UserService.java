package com.quat.Kumquat.service;


import com.quat.Kumquat.dto.UserDto;
import com.quat.Kumquat.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
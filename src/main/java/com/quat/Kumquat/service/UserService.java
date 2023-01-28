package com.quat.Kumquat.service;


import com.quat.Kumquat.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    User findUserById(long id);

    User findUserByEmail(String email);

    List<User> findAllUsers();
}
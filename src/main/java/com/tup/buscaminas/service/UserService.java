package com.tup.buscaminas.service;

import com.tup.buscaminas.entity.User;

public interface UserService {
    public User createUser(String username, String password);
    public User getUser(Long userId);
    public User updateUser(Long userId, String username, String password);
    public void deleteUser(Long userId);
}

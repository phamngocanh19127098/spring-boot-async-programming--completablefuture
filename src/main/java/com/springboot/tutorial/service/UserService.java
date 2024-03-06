package com.springboot.tutorial.service;

import com.springboot.tutorial.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<List<User>> saveAllUsers(MultipartFile multipartFile);
    CompletableFuture<List<User>> getAllUsers();
}

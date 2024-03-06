package com.springboot.tutorial.controller;

import com.springboot.tutorial.entity.User;
import com.springboot.tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(
            @RequestParam(value = "files") MultipartFile [] files
            ) throws IOException {
        for (MultipartFile file : files) {
            userService.saveAllUsers(file);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users")
    public ResponseEntity get3XAllUsers () {
        CompletableFuture<List<User>> userList1 = userService.getAllUsers();
        CompletableFuture<List<User>> userList2 = userService.getAllUsers();
        CompletableFuture<List<User>> userList3 = userService.getAllUsers();
        List<List<User>> users = Stream.of(userList1, userList2, userList3).map(CompletableFuture::join).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}

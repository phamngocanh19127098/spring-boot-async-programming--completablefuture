package com.springboot.tutorial.service;

import com.springboot.tutorial.entity.User;
import com.springboot.tutorial.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    @Async
    public CompletableFuture<List<User>> saveAllUsers(MultipartFile multipartFile) {
        log.info("start thread...");
        Long start = System.currentTimeMillis();
        List<User> users = parseFile(multipartFile);
        log.info("saving list of users of size " + users.size() + " " + Thread.currentThread().getName());
        users = userRepository.saveAll(users);
        log.info("end thread...");
        Long end = System.currentTimeMillis();
        log.info("current thread took " + (end - start));
        return CompletableFuture.completedFuture(users);
    }

    @Override
    @Async
    public CompletableFuture<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("current thread name is " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseFile (MultipartFile multipartFile) {
        final List<User> users = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                String [] data = line.split(",");
                User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return users;
    }
}

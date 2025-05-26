package com.anup.v1.JournalApp.controller;

import com.anup.v1.JournalApp.entity.User;
import com.anup.v1.JournalApp.repo.UserRepository;
import com.anup.v1.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }

    @GetMapping("id/{id}")
    public User getUserById(@PathVariable ObjectId id){
        return userService.findById(id).get();
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }
}

package com.example.VaibhavProject.service;

import com.example.VaibhavProject.model.User;
import com.example.VaibhavProject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;


    public boolean Checker(String id){
        return userRepo.existsById(id);
    }
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }

    public User insertUser(User user){
        return userRepo.save(user);
    }

    public User findById(String id){
        return userRepo.findById(id).get();
    }


}

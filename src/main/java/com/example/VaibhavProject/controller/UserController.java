package com.example.VaibhavProject.controller;

import com.example.VaibhavProject.model.Article;
import com.example.VaibhavProject.model.User;
import com.example.VaibhavProject.service.UserService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Iterable<User> getAllArticles(){
        return userService.getUsers();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user1) throws NoSuchElementException{
        String uid=user1.getUsername();
        boolean flag=userService.Checker(uid);

        if(flag){
            return null;
        }else{
            String sha256hex = Hashing.sha256()
                    .hashString(user1.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            user1.setPassword(sha256hex);
            return userService.insertUser(user1);
        }


//        return null;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) throws NoSuchElementException {
        String uid=user.getUsername();
        String upassword=user.getPassword();
        boolean flag= userService.Checker(uid);
        String sha256hex=Hashing.sha256()
                .hashString(upassword, StandardCharsets.UTF_8)
                .toString();
//        user1.setPassword(sha256hex);
        if(!flag){
            return null;
        }else{
            User u=userService.findById(uid);
            if(sha256hex.equals(u.getPassword())){
                System.out.println("successfully logged in");
                return "login success";
            }else{
                System.out.println("incorrect password");
                return "null";
            }
        }
    }



}

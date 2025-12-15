package com.mysite.backend.controller;

import com.mysite.backend.exception.UserNotFoundException;
import com.mysite.backend.model.User;
import com.mysite.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    User createUser(@RequestBody User user){ //클라이언트에서 json객체를 서버로 보냄 => User객체로 변환
        return userRepository.save(user); //유저를 저장하고 다시 리턴
    }

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll(); //db의 모든 유저를 리스트로 리턴
    }

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id){
        //id로 유저를 찾으면 유저 리턴 못찾으면 에러 발생
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }
}

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

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User newUser){
        //업데이트는 일단 id로 유저를 찾은 뒤 업데이트 함 없을경우 예외처리도 함
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "유저 아이디: "+id+"는 삭제 되었습니다.";
    }
}

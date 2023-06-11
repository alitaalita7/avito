package alito_project.controllers;

import alito_project.dto.EditAdvertisementDTO;
import alito_project.dto.EditUserDto;
import alito_project.dto.UserDto;
import alito_project.exception.UserAlreadyExist;
import alito_project.exception.UserNotFound;
import alito_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody Map<String, String> map){
        if(!map.containsKey("phone") || !map.containsKey("password"))
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        try{
            UserDto authUser = userService.auth(map.get("phone"), map.get("password"));
            return ResponseEntity.ok().body(authUser);
        }catch (UserNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody Map<String, String> map) {
        if (!map.containsKey("name") || !map.containsKey("surname") || !map.containsKey("phone") || !map.containsKey("password"))
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        try {
            UserDto user = new UserDto(map.get("name"), map.get("surname"), map.get("phone"), map.get("password"));
            userService.addUser(user);
            return ResponseEntity.ok().body(user);
        } catch (UserAlreadyExist e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Вернуть исключение UserAlreadyExist
        }
    }

    @PostMapping("/get-user-info-by-ad")
    public ResponseEntity getUserInfoByAd (@RequestBody Map<String, Integer> map){
        if (!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(userService.getUserInfoByAd(map.get("id")));
    }

    @PostMapping("/get-user-info-by-id")
    public ResponseEntity getUserInfoById (@RequestBody Map<String, Integer> map){
        if (!map.containsKey("user_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(userService.getUserInfoById(map.get("user_id")));
    }

    @PostMapping("/edit-user")
    public ResponseEntity editUser(@RequestBody Map<String, String> map){
        if(!map.containsKey("user_id") || !map.containsKey("column") || !map.containsKey("value")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        int user_id = Integer.parseInt(map.get("user_id"));
        userService.editUser(user_id, map.get("column"), map.get("value"));
        return ResponseEntity.ok().body("ok");
    }

    @PutMapping("/user/{id}")
    public ResponseEntity editUser_v1(@PathVariable int id, @RequestBody EditUserDto data){
        return ResponseEntity.ok().body(userService.editUser_v1(id, data));
    }

}

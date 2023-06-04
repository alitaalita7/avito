package alito_project.controllers;

import alito_project.dto.UserDto;
import alito_project.exception.UserAlreadyExist;
import alito_project.exception.UserNotFound;
import alito_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}

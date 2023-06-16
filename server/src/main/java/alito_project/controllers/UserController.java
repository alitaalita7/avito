package alito_project.controllers;

import alito_project.dto.AdUser;
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

    // авторизация пользователя при совпадении номера и пароля
    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody Map<String, String> map){
        if(!map.containsKey("phone") || !map.containsKey("password"))
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        try{
            AdUser authUser = userService.auth(map.get("phone"), map.get("password"));
            return ResponseEntity.ok().body(authUser);
        }catch (UserNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // регистрация пользователя по полученным данным с фронта
    // с проверкой по номеру
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody Map<String, String> map) {
        if (!map.containsKey("name") || !map.containsKey("surname") || !map.containsKey("phone") || !map.containsKey("password"))
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        try {
            UserDto user = new UserDto(map.get("name"), map.get("surname"), map.get("phone"), map.get("password"));
            return ResponseEntity.ok().body(userService.addUser(user));
        } catch (UserAlreadyExist e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // получение информации о пользователе по id его объявления
    @PostMapping("/get-user-info-by-ad")
    public ResponseEntity getUserInfoByAd (@RequestBody Map<String, Integer> map){
        if (!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(userService.getUserInfoByAd(map.get("id")));
    }

    // получение информации о пользователе по его id
    @PostMapping("/get-user-info-by-id")
    public ResponseEntity getUserInfoById (@RequestBody Map<String, Integer> map){
        if (!map.containsKey("user_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(userService.getUserInfoById(map.get("user_id")));
    }

    // редактирование профиля пользователя
    // по полученным данным с фронта путем обновления
    @PutMapping("/edit-user/{id}")
    public ResponseEntity editUser_v1(@PathVariable int id, @RequestBody EditUserDto data){
        return ResponseEntity.ok().body(userService.editUser(id, data));
    }

    // удаление профиля путем обновления поля is_blocked
    @PostMapping("/delete-user")
    public ResponseEntity deleteUser(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("user_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        userService.deleteUser(map.get("user_id"));
        return ResponseEntity.status(204).build();
    }

    // проверка на статус блокировки пользователя
    @PostMapping("/user-is-blocked")
    public ResponseEntity isBlocked(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("user_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(userService.isBlocked(map.get("user_id")));
    }

}

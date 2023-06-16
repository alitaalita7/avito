package alito_project.controllers;

import alito_project.dto.AdIsLike;
import alito_project.services.AdminService;
import alito_project.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    // получение информации об удаленных пользователях
    // с проверкой админа (чтобы нельзя было спослать запрос через постмэн)
    @PostMapping("/users")
    public ResponseEntity getBlockedUsers(@RequestBody Map<String, Integer> map) {
        if(!map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().body(adminService.getBlockedUsers());
    }

    // получение информации об удаленных объявлениях
    // с проверкой админа
    @PostMapping("/advertisements")
    public ResponseEntity getBlockedAdvertisements(@RequestBody Map<String, Integer> map) {
        if(!map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().body(adminService.getBlockedAdvertisements());
    }

    // получение информации об удаленных отзывах
    // с проверкой админа
    @PostMapping("/reviews")
    public ResponseEntity getBlockedReviews(@RequestBody Map<String, Integer> map) {
        if(!map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().body(adminService.getBlockedReviews());
    }

    // восстановление удаленного пользователя
    // путем обновления поля is_blocked
    // с проверкой админа
    @PostMapping("/recovery-user")
    public ResponseEntity recoveryUser(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("user_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.recoveryUser(map.get("user_id"));
        return ResponseEntity.status(204).build();
    }

    // восстановление удаленного объявления
    // путем обновления поля is_deleted
    // с проверкой админа
    @PostMapping("/recovery-advertisement")
    public ResponseEntity recoveryAdvertisement(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("ad_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.recoveryAdvertisement(map.get("ad_id"));
        return ResponseEntity.status(204).build();
    }

    // восстановление удаленного отзыва
    // путем обновления поля is_deleted
    // с проверкой админа
    @PostMapping("/recovery-reviews")
    public ResponseEntity recoveryReview(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("review_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.recoveryReview(map.get("review_id"));
        return ResponseEntity.status(204).build();
    }

    // удаление пользователя
    // путем обновления поля is_blocked
    // с проверкой админа
    @PostMapping("/delete-user")
    public ResponseEntity deleteUser(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("user_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.deleteUser(map.get("user_id"));
        return ResponseEntity.status(204).build();
    }

    // удаление объявления
    // путем обновления поля is_deleted
    // с проверкой админа
    @PostMapping("/delete-advertisement")
    public ResponseEntity deleteAdvertisement(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("ad_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.deleteAdvertisement(map.get("ad_id"));
        return ResponseEntity.status(204).build();
    }

    // удаление отзыва
    // путем обновления поля is_deleted
    // с проверкой админа
    @PostMapping("/delete-review")
    public ResponseEntity deleteReview(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("review_id") || !map.containsKey("admin_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        if(!adminService.checkAdmin(map.get("admin_id"))){
            return ResponseEntity.status(403).build();
        }
        adminService.deleteReview(map.get("review_id"));
        return ResponseEntity.status(204).build();
    }

}

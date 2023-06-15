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

}

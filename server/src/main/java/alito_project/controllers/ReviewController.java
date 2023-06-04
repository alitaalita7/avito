package alito_project.controllers;

import alito_project.services.ReviewService;
import alito_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/get-review-info-by-ad")
    public ResponseEntity getReviewByAdId(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        Map<String, Double> map2 = reviewService.getReviewByAdId(map.get("id"));
        return ResponseEntity.ok().body(map2);
    }
    @PostMapping("/get-review-info-by-user")
    public ResponseEntity getReviewByUserId(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        Map<String, Double> map2 = reviewService.getReviewByUserId(map.get("id"));
        return ResponseEntity.ok().body(map2);
    }
}

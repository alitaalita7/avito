package alito_project.controllers;

import alito_project.dto.AdvertisementDto;
import alito_project.dto.FavoriteDto;
import alito_project.services.AdvertisementService;
import alito_project.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/get-all-favorites")
    public ResponseEntity getAllFavorites(){
        return ResponseEntity.ok().body(favoriteService.getAllFavorites());
    }
    @PostMapping("/add-to-favorite")
    public ResponseEntity<?> addToFavorite(@RequestBody Map<String, Integer> map) {
        // TODO: добавить валидацию мапы чтобы запрос не падал когда он приходит не с фронта\
        FavoriteDto data = new FavoriteDto(
                map.get("user_id"),
                map.get("ad_id")
        );
        favoriteService.addToFavorite(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-favorites-by-id")
    public ResponseEntity getFavoritesById(@RequestBody Map<String, Integer> map){
        if(map.containsKey("user_id")){
            return ResponseEntity.ok().body(favoriteService.getFavoritesById(map.get("user_id")));
        }
        else return ResponseEntity.badRequest().body("IllegalArgumentException");
    }

    @PostMapping("/delete-from-favorites")
    public ResponseEntity deleteFromFavorites(@RequestBody Map<String, Integer> map){
        if(map.containsKey("user_id") && map.containsKey("ad_id")){
            return ResponseEntity.ok().body(favoriteService.deleteFromFavorites(map.get("user_id"), map.get("ad_id")));
        }
        else return ResponseEntity.badRequest().body("IllegalArgumentException");
    }

    @PostMapping("/is-favorite-exist")
    public ResponseEntity isFavoriteExist(@RequestBody Map<String, Integer> map){
        if(map.containsKey("user_id") && map.containsKey("ad_id")){
            boolean isFavorite = favoriteService.isFavoriteExist(map.get("user_id"), map.get("ad_id"));
            if(isFavorite){
                return ResponseEntity.ok().body("yes");
            }
            else return ResponseEntity.ok().body("no");
        }
        else return ResponseEntity.badRequest().body("IllegalArgumentException");
    }
}

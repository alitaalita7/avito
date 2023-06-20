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

    //проверка на наличие избранного по id пользователя и id объявления
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

    // добавление объявления в избранное по полученным данным с фронта
    // при проверке уже существуещей записи
    @PostMapping("/add-to-favorite")
    public ResponseEntity<?> addToFavorite(@RequestBody Map<String, Integer> map) {
        if(!map.containsKey("user_id") || !map.containsKey("ad_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        FavoriteDto data = new FavoriteDto(
                map.get("user_id"),
                map.get("ad_id")
        );
        favoriteService.addToFavorite(data);
        return ResponseEntity.ok().build();
    }

    // удаление избранного
    @PostMapping("/delete-from-favorites")
    public ResponseEntity deleteFromFavorites(@RequestBody Map<String, Integer> map){
        if(map.containsKey("user_id") && map.containsKey("ad_id")){
            return ResponseEntity.ok().body(favoriteService.deleteFromFavorites(map.get("user_id"), map.get("ad_id")));
        }
        else return ResponseEntity.badRequest().body("IllegalArgumentException");
    }

    // подсчет количества добавлений опредленного объявления в избранное
    @PostMapping("/get-count-favorites-by-ad")
    public ResponseEntity getCountFavorites(@RequestBody Map<String, Integer> map){
        if (!map.containsKey("ad_id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(favoriteService.getCountFavorites(map.get("ad_id")));
    }
}

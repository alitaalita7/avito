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
        System.out.println(data);
        return ResponseEntity.ok().build();
    }
}

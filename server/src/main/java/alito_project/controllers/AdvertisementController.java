package alito_project.controllers;

import alito_project.dto.AdIsLike;
import alito_project.dto.AdvertisementDto;
import alito_project.dto.EditAdvertisementDTO;
import alito_project.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class AdvertisementController {
    @Autowired
    AdvertisementService advertisementService;
    @GetMapping("/get-all-advertisements")
    public ResponseEntity getAllAdvertisement() {
        List<AdvertisementDto> list = advertisementService.getAllAdvertisements();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/get-all-advertisements")
    public ResponseEntity getAllAdvertisement_v1(@RequestBody Map<String, Integer> map) {
        List<AdIsLike> list = advertisementService.getAllAdvertisements_v1(map.get("user_id"));
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/get-advertisement-by-id")
    public ResponseEntity getAdvertisementById(@RequestBody Map<String, Integer> map){
        if(!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(advertisementService.getAdvertisementById(map.get("id")));
    }
    @PostMapping("/get-advertisements-by-user-id-active")
    public ResponseEntity getAdvertisementsByUserIdActive(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getAdvertisementsByUserIdActive(map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }
    @PostMapping("/get-advertisements-by-user-id-archive")
    public ResponseEntity getAdvertisementsByUserIdArchive(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getAdvertisementsByUserIdArchive(map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }
    @PostMapping("/get-favorite-advertisements")
    public ResponseEntity getFavoriteAdvertisements(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getFavoriteAdvertisements(map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }
    @PostMapping("/advertisement/add")
    public ResponseEntity<?> addAdvertisement(@RequestBody Map<String, String> map) {
        System.out.println(map);
        // TODO: добавить валидацию мапы чтобы запрос не падал когда он приходит не с фронта
        // добавлять картинки в статику
        AdvertisementDto data = new AdvertisementDto(
                map.get("title"),
                map.get("description"),
                Integer.parseInt(map.get("price")),
                map.get("category"),
                map.get("city"),
                map.get("district"),
                map.get("street"),
                map.get("house"),
                map.get("photo"),
                Integer.parseInt(map.get("user_id"))
        );
        System.out.println(map.get("user_id"));
        System.out.println(data);
        advertisementService.addAdvertisement(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/edit-ad")
    public ResponseEntity editAdvertisement(@RequestBody Map<String, String> map){
        System.out.println(123);
        if(!map.containsKey("ad_id") || !map.containsKey("column") || !map.containsKey("value")){
            System.out.println(map);
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        int ad_id = Integer.parseInt(map.get("ad_id"));
        advertisementService.editAdvertisement(ad_id, map.get("column"), map.get("value"));
        return ResponseEntity.ok().body("ok");
    }

    @PutMapping("/advertisement/{id}")
    public ResponseEntity editAdvertisement_v1(@PathVariable int id, @RequestBody EditAdvertisementDTO data){
        return ResponseEntity.ok().body(advertisementService.editAdvertisement_v1(id, data));
    }

}

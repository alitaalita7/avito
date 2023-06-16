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

    // получение всех объявлений на главную страницу
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления активные, не заблокированы, а также владельцы объявлений не заблокированы
    // и с учетом рекоммендаций
    @PostMapping("/get-all-advertisements")
    public ResponseEntity getAllAdvertisement(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("Отсутсвтует user_id");
        }
        List<AdIsLike> list = advertisementService.getAllAdvertisements(map.get("user_id"));
        return ResponseEntity.ok().body(list);
    }

    // получение информации об объявлении по его id
    @PostMapping("/get-advertisement-by-id")
    public ResponseEntity getAdvertisementById(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("id")) {
            return ResponseEntity.badRequest().body("Отсутсвтует id");
        }
        return ResponseEntity.ok().body(advertisementService.getAdvertisementById(map.get("id")));
    }

    // добавление нового объявления на основе полученных данных с фронта
    @PostMapping("/add-advertisement")
    public ResponseEntity<?> addAdvertisement(@RequestBody Map<String, String> map) {
        if(!map.containsKey("title") || !map.containsKey("description") || !map.containsKey("prise")
                || !map.containsKey("category") || !map.containsKey("city") || !map.containsKey("district")
                || !map.containsKey("street") || !map.containsKey("house") || !map.containsKey("photo")
                || !map.containsKey("user_id")){
            return ResponseEntity.badRequest().body("Получены не все поля");
        }
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
        advertisementService.addAdvertisement(data);
        return ResponseEntity.ok().build();
    }

    // получение активных объявлений определенного пользователя по id пользователя
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления активные и не удалены
    @PostMapping("/get-advertisements-by-user-id-active")
    public ResponseEntity getAdvertisementsByUserIdActive(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id") || !map.containsKey("my_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getAdvertisementsByUserIdActive(map.get("my_id"), map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }

    // получение архивных объявлений определенного пользователя по id пользователя
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления архивные и не удалены
    @PostMapping("/get-advertisements-by-user-id-archive")
    public ResponseEntity getAdvertisementsByUserIdArchive(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getAdvertisementsByUserIdArchive(map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }

    // получение только избранных объявлений
    // с отображением избранного для авторизованного пользователя
    // с проверкой что объявления активные, не удалены, а также владельцы объявлений не удалены
    @PostMapping("/get-favorite-advertisements")
    public ResponseEntity getFavoriteAdvertisements(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("user_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        List<AdIsLike> advertisements = advertisementService.getFavoriteAdvertisements(map.get("user_id"));
        return ResponseEntity.ok().body(advertisements);
    }

    // обновление объявления на основе полученных данных с фронта и id объявления
    @PutMapping("/edit-advertisement/{id}")
    public ResponseEntity editAdvertisement_v1(@PathVariable int id, @RequestBody EditAdvertisementDTO data) {
        return ResponseEntity.ok().body(advertisementService.editAdvertisement(id, data));
    }

    // архивирование объяления путем обновления статуса
    @PostMapping("/delete-advertisement-archive")
    public ResponseEntity deleteAdvertisementArchive(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("ad_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        advertisementService.deleteAdvertisementArchive(map.get("ad_id"));
        return ResponseEntity.status(204).build();
    }

    // восстановление объявления из архива обновления статуса
    @PostMapping("/recovery-advertisement")
    public ResponseEntity recoveryAdvertisement(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("ad_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        advertisementService.recoveryAdvertisement(map.get("ad_id"));
        return ResponseEntity.status(204).build();
    }

    // удаления объявления путем обновления поля is_deleted
    @PostMapping("/delete-advertisement")
    public ResponseEntity deleteAdvertisement(@RequestBody Map<String, Integer> map) {
        if (!map.containsKey("ad_id")) {
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        advertisementService.deleteAdvertisement(map.get("ad_id"));
        return ResponseEntity.status(204).build();
    }
}

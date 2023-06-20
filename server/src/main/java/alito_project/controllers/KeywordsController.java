package alito_project.controllers;

import alito_project.dto.AdIsLike;
import alito_project.dto.KeywordsDto;
import alito_project.services.FavoriteService;
import alito_project.services.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class KeywordsController {

    @Autowired
    KeywordsService keywordsService;

    // получение всех ключевых слов для селекта
    @GetMapping("/get-keywords")
    public ResponseEntity getKeywords(){
        List<KeywordsDto> list = keywordsService.getKeywords();
        return ResponseEntity.ok().body(list);
    }

    // получение ключевых слов для определенного объявления
    @PostMapping("/get-keywords-by-ad-id")
    public ResponseEntity getKeywordsByAdId(@RequestBody Map<String, Integer> map){
        if (!map.containsKey("id")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        return ResponseEntity.ok().body(keywordsService.getKeywordsByAdId(map.get("id")));
    }

    // добавление ключей
    @PostMapping("/add-keys/{id}")
    public ResponseEntity addKeys(@PathVariable int id, @RequestBody Map<String, KeywordsDto[]> map){
        if(!map.containsKey("selectedKeywords")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        keywordsService.addKeys(id, map.get("selectedKeywords"));
        return ResponseEntity.noContent().build();
    }

    // удаление ключей
    @PostMapping("/remove-keys/{id}")
    public ResponseEntity removeKeys(@PathVariable int id, @RequestBody Map<String, KeywordsDto[]> map){
        if(!map.containsKey("selectedKeywords")){
            return ResponseEntity.badRequest().body("IllegalArgumentException");
        }
        keywordsService.removeKeys(id, map.get("selectedKeywords"));
        return ResponseEntity.noContent().build();
    }

}

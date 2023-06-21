package alito_project.services;

import alito_project.dto.AdIsLike;
import alito_project.dto.FavoriteDto;
import alito_project.dto.KeywordsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KeywordsService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // получение всех ключевых слов для селекта
    public List<KeywordsDto> getKeywords() {
        String sql = "SELECT * FROM keywords\n" +
                "ORDER BY word;";
        List<KeywordsDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new KeywordsDto(
                rs.getInt("id"),
                rs.getString("word")
        ));
        return list;
    }

    // получение ключевых слов для определенного объявления
    public List<KeywordsDto> getKeywordsByAdId(int ad_id) {
        String sql = "SELECT keywords.* FROM keys \n" +
                "join keywords on keys.key_id = keywords.id\n" +
                "WHERE ad_id = ?";
        List<KeywordsDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new KeywordsDto(
                rs.getInt("id"),
                rs.getString("word")
        ), ad_id);
        return list;
    }

    // добавление ключей
    public void addKeys(int ad_id, KeywordsDto[] keywords) {
        List<KeywordsDto> allKeysForAd = getKeywordsByAdId(ad_id);

        // Создаем множество для хранения уникальных идентификаторов ключевых слов
        Set<Integer> uniqueKeywordIds = new HashSet<>();

        // Фильтруем дубликаты ключевых слов и собираем уникальные идентификаторы ключевых слов
        for (KeywordsDto keyword : keywords) {
            boolean isDuplicate = false;
            for (KeywordsDto existingKeyword : allKeysForAd) {
                if (existingKeyword.id == keyword.id) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueKeywordIds.add(keyword.id);
            }
        }
        //Вставляем уникальные идентификаторы ключевых слов в таблицу "keys"
        String sql = "INSERT INTO keys (ad_id, key_id) VALUES (?, ?)";
        for (Integer keywordId : uniqueKeywordIds) {
            jdbcTemplate.update(sql, ad_id, keywordId);
        }
    }

    // удаление ключей
    public void removeKeys(int ad_id, KeywordsDto[] keywords) {
        // Получаем все ключевые слова для объявления
        List<KeywordsDto> allKeysForAd = getKeywordsByAdId(ad_id);

        // Создаем множество для хранения уникальных идентификаторов ключевых слов для удаления
        Set<Integer> keywordIdsToRemove = new HashSet<>();

        // Ищем различающиеся ключевые слова и добавляем их идентификаторы в множество для удаления
        for (KeywordsDto existingKeyword : allKeysForAd) {
            boolean isDifferent = true;
            for (KeywordsDto keyword : keywords) {
                if (existingKeyword.id == keyword.id) {
                    isDifferent = false;
                    break;
                }
            }
            if (isDifferent) {
                keywordIdsToRemove.add(existingKeyword.id);
            }
        }

        // Удаляем ключевые слова из таблицы "keys"
        String sql = "DELETE FROM keys WHERE ad_id = ? AND key_id = ?";
        for (Integer keywordId : keywordIdsToRemove) {
            jdbcTemplate.update(sql, ad_id, keywordId);
        }
    }


}
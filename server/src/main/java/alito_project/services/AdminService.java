package alito_project.services;

import alito_project.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    // проверка на то является ли пользователь админом
    // по полученному id пользователя
    // с переиспользованием метода получения информации о пользователе по его id
    public boolean checkAdmin(int admin_id){
        UserDto user = userService.getUserInfoById(admin_id);
        return user.is_admin;
    }

    // получение информации об удаленных пользователях
    public List<UserDto> getBlockedUsers(){
        String sql = "SELECT * FROM users WHERE is_blocked = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getBoolean("is_admin"),
                rs.getBoolean("is_blocked")
        ));
    }

    // получение информации об удаленных объявлениях
    public List<AdvertisementDto> getBlockedAdvertisements(){
        String sql = "SELECT * FROM advertisements WHERE is_deleted = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AdvertisementDto(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("date_created"),
                rs.getString("status"),
                rs.getString("category"),
                rs.getString("city"),
                rs.getString("district"),
                rs.getString("street"),
                rs.getString("house"),
                rs.getString("photo"),
                rs.getInt("user_id"),
                rs.getBoolean("is_deleted")
        ));
    }

    // получение информации об удаленных отзывах
    public List<ReviewDto> getBlockedReviews(){
        String sql = "SELECT * FROM reviews WHERE is_deleted = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReviewDto(
                rs.getInt("id"),
                rs.getInt("from_user"),
                rs.getInt("to_user"),
                rs.getInt("advertisement_id"),
                rs.getInt("rating"),
                rs.getString("date_posted"),
                rs.getString("comment"),
                rs.getBoolean("is_deleted")
        ));
    }

    // восстановление удаленного пользователя
    // путем обновления поля is_blocked
    public void recoveryUser(int user_id){
        String sql = "UPDATE users SET is_blocked = false WHERE id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    // восстановление удаленного объявления
    // путем обновления поля is_deleted
    public void recoveryAdvertisement(int ad_id){
        String sql = "UPDATE advertisements SET is_deleted = false, status = 'active' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // восстановление удаленного отзыва
    // путем обновления поля is_deleted
    public void recoveryReview(int review_id){
        String sql = "UPDATE reviews SET is_deleted = false WHERE id = ?";
        jdbcTemplate.update(sql, review_id);
    }

    // удаление пользователя
    // путем обновления поля is_blocked
    public void deleteUser(int user_id){
        String sql = "UPDATE users SET is_blocked = true WHERE id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    // удаление объявления
    // путем обновления поля is_deleted
    public void deleteAdvertisement(int ad_id){
        String sql = "UPDATE advertisements SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // удаление отзыва
    // путем обновления поля is_deleted
    public void deleteReview(int review_id){
        String sql = "UPDATE reviews SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, review_id);
    }

    //добавление нового ключевого слова
    public void addKeyword(KeywordsDto data){
        System.out.println(data.word);
        String sql = "INSERT INTO keywords(word) VALUES (?)";
        jdbcTemplate.update(sql, data.word);
    }
    public void removeKeyword(int keywordId){
        // Удаление связей в таблице keys
        String deleteKeysQuery = "DELETE FROM keys WHERE key_id = ?";
        jdbcTemplate.update(deleteKeysQuery, keywordId);

        // Удаление ключевого слова из таблицы keywords
        String deleteKeywordQuery = "DELETE FROM keywords WHERE id = ?";
        jdbcTemplate.update(deleteKeywordQuery, keywordId);
    }
}

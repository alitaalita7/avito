package alito_project.services;

import alito_project.dto.AdvertisementDto;
import alito_project.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // получение числовой информации об отзывах пользователя по id его объявления
    // с переиспользованиям метода подсчета
    public Map<String, Double> getReviewByAdId(int ad_id) {
        String sql = "SELECT user_id FROM advertisements WHERE id= ? ";
        int user_id = jdbcTemplate.queryForObject(sql, Integer.class, ad_id);
        return getReviewByUserId(user_id);
    }

    // вычисление количества отзывов и их среднего значения по id пользователя
    // с проверкой на то что отзыв не удален и его владелец не удален
    public Map<String, Double> getReviewByUserId(int user_id) {
        String sql = "SELECT COUNT(*) FROM reviews\n" +
                "join users on reviews.from_user = users.id\n" +
                "WHERE to_user = ? and is_deleted = false and users.is_blocked = false";
        Double count = jdbcTemplate.queryForObject(sql, Double.class, user_id);

        sql = "SELECT SUM(rating) FROM reviews\n" +
                "join users on reviews.from_user = users.id\n" +
                "WHERE to_user = ? and is_deleted = false and users.is_blocked = false";
        Double sum = jdbcTemplate.queryForObject(sql, Double.class, user_id);

        Map<String, Double> map = new HashMap<>();

        if (count == null || sum == null) {
            map.put("avg", 0.0);
            map.put("count", 0.0);
            return map;
        }

        double avg = sum / count;

        map.put("avg", avg);
        map.put("count", count);

        return map;
    }

    // получение информации о полученных отзывах по id пользователя
    // с проверкой на то что отзыв не был удален
    public List<ReviewDto> getReceivedReviews(int user_id) {
        String sql = "SELECT reviews.id, reviews.from_user, reviews.to_user, reviews.advertisement_id, reviews.rating, reviews.date_posted, reviews.comment, advertisements.title, users.name, users.surname \n" +
                "FROM reviews \n" +
                "JOIN advertisements ON reviews.advertisement_id = advertisements.id \n" +
                "JOIN users ON reviews.from_user = users.id\n" +
                "WHERE reviews.to_user = ? and reviews.is_deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReviewDto(
                rs.getInt("id"),
                rs.getInt("from_user"),
                rs.getInt("to_user"),
                rs.getInt("advertisement_id"),
                rs.getInt("rating"),
                rs.getString("date_posted"),
                rs.getString("comment"),
                rs.getString("title"),
                rs.getString("name"),
                rs.getString("surname")
        ), user_id);
    }

    // получение информации об отправленных отзывах по id пользователя
    // с проверкой на то что отзыв не был удален, и пользователь которому отправляют отзыв не удален
    public List<ReviewDto> getSentReviews(int user_id) {
        String sql = "SELECT reviews.id, reviews.from_user, reviews.to_user, reviews.advertisement_id, reviews.rating, reviews.date_posted, reviews.comment, advertisements.title, users.name, users.surname \n" +
                "FROM reviews \n" +
                "JOIN advertisements ON reviews.advertisement_id = advertisements.id \n" +
                "JOIN users ON reviews.to_user = users.id\n" +
                "WHERE reviews.from_user = ? and reviews.is_deleted = false and users.is_blocked = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReviewDto(
                rs.getInt("id"),
                rs.getInt("from_user"),
                rs.getInt("to_user"),
                rs.getInt("advertisement_id"),
                rs.getInt("rating"),
                rs.getString("date_posted"),
                rs.getString("comment"),
                rs.getString("title"),
                rs.getString("name"),
                rs.getString("surname")
        ), user_id);
    }

    // добавление нового отзывы по данным полученным с фронта
    public void addReview(ReviewDto data) {
        String sql = "INSERT INTO reviews " +
                "(from_user, to_user, advertisement_id, rating, date_posted, comment) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        jdbcTemplate.update(sql, data.from_user, data.to_user, data.advertisement_id, data.rating,
                data.comment);
    }
}

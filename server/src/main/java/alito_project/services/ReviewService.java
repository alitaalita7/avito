package alito_project.services;

import alito_project.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<String, Double> getReviewByAdId(int ad_id){
        String sql = "SELECT user_id FROM advertisements WHERE id= ? LIMIT 1";
        int user_id = jdbcTemplate.queryForObject(sql, Integer.class, ad_id);
        System.out.println("user = "+ user_id);
        sql = "SELECT COUNT(*) FROM reviews WHERE to_user = ? ";
        double count = jdbcTemplate.queryForObject(sql, Double.class, user_id);
        System.out.println("count = "+count);
        sql = "SELECT SUM(rating) FROM reviews WHERE to_user= ? ";
        double sum = jdbcTemplate.queryForObject(sql, Double.class, user_id);
        System.out.println("sum = "+sum);
        double avg = sum/count;
        System.out.println("avg = "+avg);
        Map<String, Double> map = new HashMap<>();
        map.put("avg", avg);
        map.put("count", count);
        return map;
    }

    public Map<String, Double> getReviewByUserId(int user_id){
        String sql = "SELECT COUNT(*) FROM reviews WHERE to_user = ? ";
        double count = jdbcTemplate.queryForObject(sql, Double.class, user_id);
        sql = "SELECT SUM(rating) FROM reviews WHERE to_user= ? ";
        double sum = jdbcTemplate.queryForObject(sql, Double.class, user_id);
        double avg = sum/count;
        Map<String, Double> map = new HashMap<>();
        map.put("avg", avg);
        map.put("count", count);
        return map;
    }
}

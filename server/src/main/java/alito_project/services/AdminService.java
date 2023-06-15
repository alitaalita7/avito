package alito_project.services;

import alito_project.dto.AdUser;
import alito_project.dto.AdvertisementDto;
import alito_project.dto.ReviewDto;
import alito_project.dto.UserDto;
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
    public boolean checkAdmin(int admin_id){
        UserDto user = userService.getUserInfoById(admin_id);
        return user.is_admin;
    }
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

    public void recoveryUser(int user_id){
        String sql = "UPDATE users SET is_blocked = false WHERE id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    public void recoveryAdvertisement(int ad_id){
        String sql = "UPDATE advertisements SET is_deleted = false WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    public void recoveryReview(int review_id){
        String sql = "UPDATE reviews SET is_deleted = false WHERE id = ?";
        jdbcTemplate.update(sql, review_id);
    }
}

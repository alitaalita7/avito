package alito_project.services;

import alito_project.dto.AdvertisementDto;
import alito_project.dto.FavoriteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<FavoriteDto> getAllFavorites(){
        String sql = "SELECT * FROM favorites";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new FavoriteDto(
                rs.getInt("user_id"),
                rs.getInt("ad_id")
        ));
    }

    public List<FavoriteDto> getFavoritesById(int user_id){
        String sql = "SELECT * FROM favorites where user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new FavoriteDto(
                rs.getInt("user_id"),
                rs.getInt("ad_id")
        ), user_id);
    }

    public boolean isFavoriteExist(int user_id, int ad_id){
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? and ad_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user_id, ad_id) != 0;
    }
    public void addToFavorite(FavoriteDto data) {
        if(!isFavoriteExist(data.user_id, data.ad_id)){
            String sql = "INSERT INTO favorites " +
                    "(user_id, ad_id) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, data.user_id, data.ad_id);
        }
    }

    public List<FavoriteDto> deleteFromFavorites(int user_id, int ad_id){
        String sql= "DELETE FROM favorites where user_id = ? and ad_id = ?";
        jdbcTemplate.update(sql, user_id, ad_id);
        return getFavoritesById(user_id);
    }

}

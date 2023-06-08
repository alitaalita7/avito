package alito_project.services;

import alito_project.dto.AdvertisementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertisementService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<AdvertisementDto> getAllAdvertisements() {
        String sql = "SELECT * FROM advertisements";
        List<AdvertisementDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdvertisementDto(
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
                rs.getInt("user_id")
        ));
        return list;
    }

    public AdvertisementDto getAdvertisementById(int id) {
        String sql = "SELECT * FROM advertisements WHERE id=?";
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
                rs.getInt("user_id")
        ), id).get(0);
    }
    public void addAdvertisement(AdvertisementDto data) {
        String sql = "INSERT INTO advertisements " +
                "(title, description, price, date_created, status, category, city, district, street, house, photo, user_id) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, 'active', ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, data.title, data.description, data.price, data.category,
                data.city, data.district, data.street, data.house, data.photo, data.user_id
        );
    }

    public List<AdvertisementDto> getAdvertisementsByUserIdActive(int id) {
        String sql = "SELECT * FROM advertisements WHERE user_id=? and status = 'active'";
        List<AdvertisementDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdvertisementDto(
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
                rs.getInt("user_id")
        ), id);
        return list;
    }

    public List<AdvertisementDto> getAdvertisementsByUserIdArchive(int id) {
        String sql = "SELECT * FROM advertisements WHERE user_id=? and status = 'archive'";
        List<AdvertisementDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdvertisementDto(
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
                rs.getInt("user_id")
        ), id);
        return list;
    }

    public List<AdvertisementDto> getFavoriteAdvertisements(int id){
        String sql = "SELECT advertisements.*\n" +
                "FROM favorites\n" +
                "JOIN advertisements ON favorites.ad_id = advertisements.id\n" +
                "WHERE favorites.user_id = ?";
        List<AdvertisementDto> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdvertisementDto(
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
                rs.getInt("user_id")
        ), id);
        return list;
    }

    public void editAdvertisement(int ad_id, String column, String value){
        String sql = "UPDATE advertisements SET " + column + " = '" + value + "' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

}

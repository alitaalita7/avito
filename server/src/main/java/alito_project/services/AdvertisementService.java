package alito_project.services;

import alito_project.dto.AdIsLike;
import alito_project.dto.AdvertisementDto;
import alito_project.dto.EditAdvertisementDTO;
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

    public List<AdIsLike> getAllAdvertisements_v1(int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as \"isLike\"\n" +
                "FROM advertisements where status = 'active'";
        List<AdIsLike> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdIsLike(
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
                rs.getInt("isLike")
        ) ,user_id);
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

    public List<AdIsLike> getAdvertisementsByUserIdActive(int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as \"isLike\"\n" +
                "FROM advertisements WHERE user_id=? and status = 'active'";
        List<AdIsLike> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdIsLike(
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
                rs.getInt("isLike")
        ), user_id, user_id);
        return list;
    }

    public List<AdIsLike> getAdvertisementsByUserIdArchive(int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as \"isLike\"\n" +
                "FROM advertisements WHERE user_id=? and status = 'archive'";
        List<AdIsLike> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdIsLike(
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
                rs.getInt("isLike")
        ), user_id, user_id);
        return list;
    }

    public List<AdIsLike> getFavoriteAdvertisements(int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as isLike\n" +
                "FROM advertisements WHERE cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) = 1";
        List<AdIsLike> list = new ArrayList<>();
        list = jdbcTemplate.query(sql, (rs, rowNum) -> new AdIsLike(
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
                rs.getInt("isLike")
        ), user_id, user_id);
        return list;
    }

    public void editAdvertisement(int ad_id, String column, String value) {
        String sql = "UPDATE advertisements SET " + column + " = '" + value + "' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    public AdvertisementDto editAdvertisement_v1(int ad_id, EditAdvertisementDTO data) {
        String sql = "UPDATE public.advertisements " +
                " SET title=?, description=?, price=?, category=?, city=?, district=?, street=?, house=?, photo=? " +
                "WHERE id = ?";
        System.out.println(ad_id);
        jdbcTemplate.update(sql, data.title, data.description, data.price, data.category, data.city,
                data.district, data.street, data.house, data.photo, ad_id);

        return getAdvertisementById(ad_id);
    }

}

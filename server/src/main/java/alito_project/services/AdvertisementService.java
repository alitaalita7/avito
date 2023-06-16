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

    // получение всех объявлений на главную страницу
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления активные, не удалены, а также владельцы объявлений не удалены
    // и с учетом рекоммендаций
    public List<AdIsLike> getAllAdvertisements(int user_id) {
        String sql = "SELECT advertisements.*,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as isLike\n" +
                "FROM advertisements\n" +
                "join users on user_id = users.id\n" +
                "where status = 'active' and is_deleted = false and users.is_blocked = false";
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

    // получение информации об объявлении по его id
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

    // добавление нового объявления на основе полученных данных с фронта
    public void addAdvertisement(AdvertisementDto data) {
        String sql = "INSERT INTO advertisements " +
                "(title, description, price, date_created, status, category, city, district, street, house, photo, user_id) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, 'active', ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, data.title, data.description, data.price, data.category,
                data.city, data.district, data.street, data.house, data.photo, data.user_id
        );
    }

    // получение активных объявлений определенного пользователя по id пользователя
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления активные и не удалены
    public List<AdIsLike> getAdvertisementsByUserIdActive(int my_id, int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as \"isLike\"\n" +
                "FROM advertisements WHERE user_id=? and status = 'active' and is_deleted = false";
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
        ), my_id, user_id);
        return list;
    }

    // получение архивных объявлений определенного пользователя по id пользователя
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления архивные и не удалены
    public List<AdIsLike> getAdvertisementsByUserIdArchive(int user_id) {
        String sql = "SELECT *,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as \"isLike\"\n" +
                "FROM advertisements WHERE user_id=? and status = 'archive' and is_deleted = false";
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

    // получение только избранных объявлений
    // с отображением избранного для авторизованного пользователя
    // с проверкой что объявления активные, не удалены, а также владельцы объявлений не удалены
    public List<AdIsLike> getFavoriteAdvertisements(int user_id) {
        String sql = "SELECT advertisements.*,\n" +
                "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as isLike\n" +
                "FROM advertisements \n" +
                "join users on user_id = users.id\n" +
                "WHERE \n" +
                "\tcast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) = 1 \n" +
                "\tand status = 'active' \n" +
                "\tand is_deleted = false \n" +
                "\tand users.is_blocked = false";
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

    // обновление объявления на основе полученных данных с фронта и id объявления
    public AdvertisementDto editAdvertisement(int ad_id, EditAdvertisementDTO data) {
        String sql = "UPDATE public.advertisements " +
                " SET title=?, description=?, price=?, category=?, city=?, district=?, street=?, house=?, photo=? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, data.title, data.description, data.price, data.category, data.city,
                data.district, data.street, data.house, data.photo, ad_id);

        return getAdvertisementById(ad_id);
    }

    // архивирование объяления путем обновления статуса
    public void deleteAdvertisementArchive(int ad_id){
        String sql = "UPDATE advertisements SET status = 'archive' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // восстановление объявления из архива обновления статуса
    public void recoveryAdvertisement(int ad_id){
        String sql = "UPDATE advertisements SET status = 'active' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // удаления объявления путем обновления поля is_deleted
    public void deleteAdvertisement(int ad_id){
        String sql = "UPDATE advertisements SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }



}

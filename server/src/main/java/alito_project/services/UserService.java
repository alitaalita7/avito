package alito_project.services;

import alito_project.dto.*;
import alito_project.exception.UserAlreadyExist;
import alito_project.exception.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    // проверка на наличие пользователя по номеру телефона
    public boolean isSign(String phone){
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, phone) != 0;
    }

    // получение информации о пользователе по номеру и паролю (для авторизации)
    public AdUser getUser(String phone, String password){
        return jdbcTemplate.query("SELECT * FROM users WHERE phone = ? and password = ?",
                (rs, rowNum) -> new AdUser(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin"),
                        rs.getBoolean("is_blocked")
                ), phone, password
        ).get(0);
    }

    // авторизация пользователя при совпадении номера и пароля
    public AdUser auth(String phone, String password) throws UserNotFound {
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ? and password = ?";
        if(jdbcTemplate.queryForObject(sql, Integer.class, phone, password) == 1){
            return getUser(phone, password);
        } else throw new UserNotFound("Incorrect login or password");
    }

    // регистрация пользователя по полученным данным с фронта
    // с проверкой по номеру
    public AdUser addUser(UserDto user) throws UserAlreadyExist {
        if(isSign(user.phone)){
            throw new UserAlreadyExist("Пользователь с таким номером телефона уже существует");
        }
        String sql = "INSERT INTO users(name, surname, phone, password, is_admin, is_blocked)" +
                "VALUES (?,?,?,?,false, false)";
        jdbcTemplate.update(sql, user.name, user.surname, user.phone, user.password);
        return getUser(user.phone, user.password);
    }

    // получение информации о пользователе по id его объявления
    public UserDto getUserInfoByAd(int ad_id){
        String sql = "SELECT users.*\n" +
                "FROM advertisements\n" +
                "JOIN users ON advertisements.user_id = users.id\n" +
                "WHERE advertisements.id = ?\n";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getBoolean("is_admin"),
                rs.getBoolean("is_blocked")
        ), ad_id).get(0);
    }

    // получение информации о пользователе по его id
    public UserDto getUserInfoById(int user_id){
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getBoolean("is_admin"),
                rs.getBoolean("is_blocked")
        ), user_id).get(0);
    }

    // редактирование профиля пользователя
    // по полученным данным с фронта путем обновления
    public UserDto editUser(int id, EditUserDto data) {
        String sql = "UPDATE public.users " +
                " SET name=?, surname=?, password=? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, data.name, data.surname, data.password, id);
        return getUserInfoById(id);
    }

    // удаление профиля путем обновления поля is_blocked
    public void deleteUser(int user_id){
        String sql = "UPDATE users SET is_blocked = true WHERE id = ?";
        jdbcTemplate.update(sql, user_id);
    }

    // проверка на статус блокировки пользователя
    public boolean isBlocked(int user_id){
        String sql = "SELECT COUNT(*) FROM users WHERE is_blocked = true and id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user_id) != 0;
    }
}

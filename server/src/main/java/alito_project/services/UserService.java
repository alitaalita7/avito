package alito_project.services;

import alito_project.dto.AdvertisementDto;
import alito_project.dto.UserDto;
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

    public boolean isSign(String phone){
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, phone) != 0;
    }

    public UserDto getUser(String phone, String password){
        return jdbcTemplate.query("SELECT * FROM users WHERE phone = ? and password = ?",
                (rs, rowNum) -> new UserDto(
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

    public UserDto auth(String phone, String password) throws UserNotFound {
        String sql = "SELECT COUNT(*) FROM users WHERE phone = ? and password = ?";
        if(jdbcTemplate.queryForObject(sql, Integer.class, phone, password) == 1){
            return getUser(phone, password);
        } else throw new UserNotFound("Incorrect login or password");
    }

    public UserDto addUser(UserDto user) throws UserAlreadyExist {
        if(isSign(user.phone)){
            throw new UserAlreadyExist("Пользователь с таким номером телефона уже существует");
        }
        String sql = "INSERT INTO users(name, surname, phone, password, is_admin, is_blocked)" +
                "VALUES (?,?,?,?,false, false)";
        jdbcTemplate.update(sql, user.name, user.surname, user.phone, user.password);
        return getUser(user.phone, user.password);
    }

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
}

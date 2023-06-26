package alito_project.services;

import alito_project.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    KeywordsService keywordsService;

    // получение всех объявлений на главную страницу
    // с проверкой на избранное для авторизованного пользователя
    // с проверкой что объявления активные, не удалены, а также владельцы объявлений не удалены
    // и с учетом рекоммендаций
    public List<AdRecommend> getAllAdvertisements(int user_id, int page, int pageSize) {

        // Вычисление смещения для текущей страницы
        int offset = (page - 1) * pageSize;

        // проверка на то, что у пользователя есть избранное объявление
        String checkCountUserFavorites = "SELECT cast(COUNT(*) as integer) FROM favorites WHERE user_id = ?";
        Integer countUserFavorites = jdbcTemplate.queryForObject(checkCountUserFavorites, Integer.class, user_id);

        // если избранных нет, то получаем объявления без подбора рекоммендаций
        if (countUserFavorites == 0) {
            List<AdRecommend> advertisements = new ArrayList<>();
            String sql = "SELECT advertisements.*,\n" +
                    "cast((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id and favorites.user_id = ?) as integer) as isLike\n" +
                    "FROM advertisements\n" +
                    "join users on user_id = users.id\n" +
                    "where status = 'active' and is_deleted = false and users.is_blocked = false";
            advertisements = getAdvertisements(sql, user_id, 0);

            if (offset + 20 > advertisements.toArray().length) {
                advertisements = advertisements.subList(offset, advertisements.toArray().length);
            } else advertisements = advertisements.subList(offset, offset + 20);

            return advertisements;
        }

//        // Получение id последнего добавленного в избранное объявления для пользователя
//        String favoriteAdSql = "SELECT ad_id FROM favorites WHERE user_id = ? ORDER BY id DESC LIMIT 1";
//        Integer favoriteAdId = jdbcTemplate.queryForObject(favoriteAdSql, Integer.class, user_id);

        // Получение ключевых слов последнего добавленного в избранное объявления
        String favoriteAdKeywordsSql = "SELECT keywords.id, keywords.word \n" +
                "FROM advertisements \n" +
                "JOIN keys ON advertisements.id = keys.ad_id \n" +
                "JOIN keywords ON keys.key_id = keywords.id\n" +
                "WHERE advertisements.id = CAST((SELECT ad_id FROM favorites WHERE user_id = ? ORDER BY id DESC LIMIT 1) AS INTEGER)";
        List<KeywordsDto> favoriteKeywords = getKW(favoriteAdKeywordsSql, user_id);

//        // Составление списка ключевых слов последнего добавленного в избранное объявления
//        List<String> favoriteKeywords = favoriteAdKeywords.stream()
//                .map(keyword -> (String) keyword.get("word"))
//                .collect(Collectors.toList());

        // Получение всех объявлений с подсчетом совпадающих ключевых слов
        String advertisementsSql = "SELECT advertisements.*,\n" +
                "CAST((SELECT COUNT(*) FROM favorites WHERE favorites.ad_id = advertisements.id AND favorites.user_id = ?) AS INTEGER) AS isLike,\n" +
                "COUNT(keywords.id) AS keywordMatches\n" +
                "FROM advertisements\n" +
                "LEFT JOIN keys ON advertisements.id = keys.ad_id\n" +
                "LEFT JOIN keywords ON keys.key_id = keywords.id\n" +
                "JOIN users ON advertisements.user_id = users.id\n" +
                "WHERE advertisements.status = 'active' AND advertisements.is_deleted = false AND users.is_blocked = false\n" +
                "GROUP BY advertisements.id\n" +
                "ORDER BY keywordMatches DESC";

        List<AdRecommend> advertisements = jdbcTemplate.query(advertisementsSql, (rs, rowNum) -> {
            int adId = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int price = rs.getInt("price");
            String dateCreated = rs.getString("date_created");
            String status = rs.getString("status");
            String category = rs.getString("category");
            String city = rs.getString("city");
            String district = rs.getString("district");
            String street = rs.getString("street");
            String house = rs.getString("house");
            String photo = rs.getString("photo");
            int ownerId = rs.getInt("user_id");
            int isLike = rs.getInt("isLike");

            // Получение ключевых слов текущего объявления
            String adKeywordsSql = "SELECT keywords.id, keywords.word " +
                    "FROM advertisements " +
                    "JOIN keys ON advertisements.id = keys.ad_id " +
                    "JOIN keywords ON keys.key_id = keywords.id " +
                    "WHERE advertisements.id = ?";

//            List<Map<String, Object>> adKeywords = jdbcTemplate.queryForList(adKeywordsSql, adId);
//
//            // Сравнение ключевых слов текущего объявления с ключевыми словами избранного объявления
//            List<String> adKeywordWords = adKeywords.stream()
//                    .map(keyword -> (String) keyword.get("word"))
//                    .collect(Collectors.toList());

            List<KeywordsDto> adKeywordWord = getKW(adKeywordsSql, adId );

            int keywordMatches = calculateKeywordMatches(favoriteKeywords, adKeywordWord);

            return new AdRecommend(adId, title, description, price, dateCreated, status, category, city,
                    district, street, house, photo, ownerId, isLike, keywordMatches);
        }, user_id);

        // Сортировка объявлений по количеству совпадений ключевых слов
        advertisements.sort((ad1, ad2) -> Integer.compare(ad2.keywordMatches, ad1.keywordMatches));

        if (offset + 20 > advertisements.toArray().length) {
            advertisements = advertisements.subList(offset, advertisements.toArray().length);
        } else advertisements = advertisements.subList(offset, offset + 20);

        return advertisements;
    }


    private List<KeywordsDto> getKW(String sql, int id) {

        return jdbcTemplate.query(sql,
            (rs, rowNum) -> new KeywordsDto(
                    rs.getInt("id"),
                    rs.getString("word")
        ), id);
    }

    public int getTotalPagesCount() {
        String totalAdvertisementsCountSql = "SELECT COUNT(*) FROM advertisements \n" +
                "JOIN users ON advertisements.user_id = users.id\n" +
                "WHERE advertisements.status = 'active' AND advertisements.is_deleted = false AND users.is_blocked = false";
        int totalAdvertisementsCount = jdbcTemplate.queryForObject(totalAdvertisementsCountSql, Integer.class); // Получите общее количество объявлений
        int pageSize = 20; // Максимальное количество объявлений на странице
        int totalPages = (int) Math.ceil((double) totalAdvertisementsCount / pageSize);
        return totalPages;
    }

    private List<AdRecommend> getAdvertisements(String sql, int user_id, int keywordMathes) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new AdRecommend(
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
                rs.getInt("isLike"),
                keywordMathes
        ), user_id);
    }

    private int calculateKeywordMatches(List<KeywordsDto> favoriteKeywords, List<KeywordsDto> adKeywords) {
        int matches = 0;
        for (KeywordsDto keyword : favoriteKeywords) {
            for (KeywordsDto k : adKeywords) {
                if (k.word.equals(keyword.word)) {
                    matches++;
                }
            }
        }
        return matches;
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
    // создание нового объявления в бд,получение id нового объявления,переиспользование метода добавления ключей
    public void addAdvertisement(AdAdvertisement data, KeywordsDto[] keywords) {
        String sql = "INSERT INTO advertisements " +
                "(title, description, price, date_created, status, category, city, district, street, house, photo, user_id) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, 'active', ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, data.title, data.description, data.price, data.category,
                data.city, data.district, data.street, data.house, data.photo, data.user_id
        );
        sql = "SELECT id FROM advertisements WHERE title = ? AND description = ? AND price = ? " +
                "AND category = ? AND city = ? AND district = ? AND street = ? AND house = ? AND photo = ? AND user_id = ?";

        int adId = jdbcTemplate.queryForObject(sql, Integer.class, data.title, data.description, data.price,
                data.category, data.city, data.district, data.street, data.house, data.photo, data.user_id);

        keywordsService.addKeys(adId, keywords);
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
    public void deleteAdvertisementArchive(int ad_id) {
        String sql = "UPDATE advertisements SET status = 'archive' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // восстановление объявления из архива обновления статуса
    public void recoveryAdvertisement(int ad_id) {
        String sql = "UPDATE advertisements SET status = 'active' WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }

    // удаления объявления путем обновления поля is_deleted
    public void deleteAdvertisement(int ad_id) {
        String sql = "UPDATE advertisements SET is_deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, ad_id);
    }


}

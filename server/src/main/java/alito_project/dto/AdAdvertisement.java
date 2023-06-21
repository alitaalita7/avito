package alito_project.dto;

public class AdAdvertisement {

    public int id;
    public String title;
    public String description;
    public int price;
    public String category;
    public String city;
    public String district;
    public String street;
    public String house;
    public String photo;
    public int user_id;
    public KeywordsDto[] keywords;

    public AdAdvertisement(String title, String description, int price, String category, String city, String district, String street, String house, String photo, int user_id, KeywordsDto[] keywords) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.city = city;
        this.district = district;
        this.street = street;
        this.house = house;
        this.photo = photo;
        this.user_id = user_id;
        this.keywords = keywords;
    }
}

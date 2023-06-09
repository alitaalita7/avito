package alito_project.dto;

public class AdIsLike {

    public int id;
    public String title;
    public String description;
    public int price;
    public String date_created;
    public String status;
    public String category;
    public String city;
    public String district;
    public String street;
    public String house;
    public String photo;
    public int user_id;
    public int isLike;

    public AdIsLike(int id, String title, String description, int price, String date_created, String status, String category, String city, String district, String street, String house, String photo, int user_id, int isLike) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date_created = date_created;
        this.status = status;
        this.category = category;
        this.city = city;
        this.district = district;
        this.street = street;
        this.house = house;
        this.photo = photo;
        this.user_id = user_id;
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "AdIsLike{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", date_created='" + date_created + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", photo='" + photo + '\'' +
                ", user_id=" + user_id +
                ", isLike=" + isLike +
                '}';
    }
}

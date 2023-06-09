package alito_project.dto;

public class EditAdvertisementDTO {

    public String title;
    public String description;
    public int price;
    public String category;
    public String city;
    public String district;
    public String street;
    public String house;
    public String photo;

    public EditAdvertisementDTO(String title, String description, int price, String category, String city, String district, String street, String house, String photo) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.city = city;
        this.district = district;
        this.street = street;
        this.house = house;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "EditAdvertisementDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

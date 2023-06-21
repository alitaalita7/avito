package alito_project.dto;

public class AdRecommend {
    public int id;
    public String title;
    public String description;
    public int    price;
    public String  dateCreated;
    public String     status;
    public String     category;
    public String     city;
    public String    district;
    public String    street;
    public String   house;
    public String   photo;
    public int    ownerId;
    public int    isLike;
    public int    keywordMatches;

    public AdRecommend(int id, String title, String description, int price, String dateCreated, String status, String category, String city, String district, String street, String house, String photo, int ownerId, int isLike, int keywordMatches) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dateCreated = dateCreated;
        this.status = status;
        this.category = category;
        this.city = city;
        this.district = district;
        this.street = street;
        this.house = house;
        this.photo = photo;
        this.ownerId = ownerId;
        this.isLike = isLike;
        this.keywordMatches = keywordMatches;
    }
}

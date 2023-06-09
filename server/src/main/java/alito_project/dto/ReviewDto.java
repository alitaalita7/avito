package alito_project.dto;

public class ReviewDto {
    public int id;
    public int from_user;
    public int to_user;
    public int advertisement_id;
    public int rating;
    public String date_posted;
    public String comment;
    public String title;
    public String name;
    public String surname;
    public boolean is_deleted;

    public ReviewDto(int id, int from_user, int to_user, int advertisement_id, int rating, String date_posted, String comment, boolean is_deleted) {
        this.id = id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.advertisement_id = advertisement_id;
        this.rating = rating;
        this.date_posted = date_posted;
        this.comment = comment;
        this.is_deleted = is_deleted;
    }

    public ReviewDto(int id, int from_user, int to_user, int advertisement_id, int rating, String date_posted, String comment, String title, String name, String surname) {
        this.id = id;
        this.from_user = from_user;
        this.to_user = to_user;
        this.advertisement_id = advertisement_id;
        this.rating = rating;
        this.date_posted = date_posted;
        this.comment = comment;
        this.title = title;
        this.name = name;
        this.surname = surname;
    }

    public ReviewDto(int from_user, int to_user, int advertisement_id, int rating, String comment) {
        this.from_user = from_user;
        this.to_user = to_user;
        this.advertisement_id = advertisement_id;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                ", advertisement_id=" + advertisement_id +
                ", rating=" + rating +
                ", date_posted='" + date_posted + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

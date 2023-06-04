package alito_project.dto;

public class FavoriteDto {
    public  int user_id;
    public int ad_id;

    public FavoriteDto(int user_id, int ad_id) {
        this.user_id = user_id;
        this.ad_id = ad_id;
    }

    @Override
    public String toString() {
        return "FavoriteDto{" +
                ", user_id=" + user_id +
                ", ad_id=" + ad_id +
                '}';
    }
}

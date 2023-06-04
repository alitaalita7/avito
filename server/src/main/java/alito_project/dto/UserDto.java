package alito_project.dto;

public class UserDto {
    public int id;
    public String name;
    public String surname;
    public String phone;
    public String password;
    public boolean is_admin;
    public boolean is_blocked;

    public UserDto(int id, String name, String surname, String phone, String password, boolean is_admin, boolean is_blocked) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
        this.is_admin = is_admin;
        this.is_blocked = is_blocked;
    }

    public UserDto(String name, String surname, String phone, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                ", is_blocked=" + is_blocked +
                '}';
    }
}

package alito_project.dto;

public class EditUserDto {
        public String name;
        public String surname;
        public String password;

    public EditUserDto(String name, String surname, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}

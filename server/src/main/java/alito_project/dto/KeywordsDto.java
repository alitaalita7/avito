package alito_project.dto;

public class KeywordsDto {
    public int id;
    public String word;

    public KeywordsDto(int id, String word) {
        this.id = id;
        this.word = word;
    }
    public KeywordsDto(String word){
        this.word=word;
    }

    @Override
    public String toString() {
        return "KeywordsDto{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}

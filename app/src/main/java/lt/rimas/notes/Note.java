package lt.rimas.notes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {

    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Note() {

    }

    public Note(
            int id,
            String title,
            String description
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updateDate = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updateDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s\nTitle: %s\nDescription:\n\t%s\n\t%s\n\t%s",
                this.id,
                this.title,
                this.description,
                this.creationDate.format(formatter),
                this.updateDate.format(formatter)
        );
    }
}

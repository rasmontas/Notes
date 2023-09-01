package lt.rimas.notes;

import static lt.rimas.notes.Note.NOTE_TABLE_NAME;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity (tableName = NOTE_TABLE_NAME)
public class Note  {

    @Ignore
    public static final String NOTE_TABLE_NAME = "note";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @Ignore
    private LocalDateTime creationDate;
    @Ignore
    private LocalDateTime updateDate;
    @Ignore
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Ignore
    public Note() {

    }

    public Note(

            String title,
            String description
    ) {


        this.title = title;
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }


    @Ignore
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "id: %s\nTitle: %s\nDescription:\n\t%s\n\t%s\n\t%s",
                this.id,
                this.title,
                this.description,
                this.creationDate != null ? this.creationDate.format(formatter) : "no data",
                this.updateDate != null ? this.updateDate.format(formatter) : "no data"
        );
    }}



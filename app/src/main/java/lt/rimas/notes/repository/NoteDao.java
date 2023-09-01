package lt.rimas.notes.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import lt.rimas.notes.Note;

@Dao
public interface NoteDao {

    @Query(" SELECT * FROM " + Note.NOTE_TABLE_NAME)
    List<Note> getAll();

    @Query(" SELECT * FROM " + Note.NOTE_TABLE_NAME + " WHERE note_id =:id ")
    Note getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(List<Note> notes);

    @Delete
    void delete(Note note);
}

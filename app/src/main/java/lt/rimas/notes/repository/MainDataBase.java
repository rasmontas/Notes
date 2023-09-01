package lt.rimas.notes.repository;


import static lt.rimas.notes.repository.MainDataBase.DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import lt.rimas.notes.Note;

@Database(
        entities = {Note.class},
        version = DATABASE_VERSION,
        exportSchema = false
)
public abstract class MainDataBase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main.db";

    private static MainDataBase instance;

    public static synchronized MainDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context,
                            MainDataBase.class,
                            DATABASE_NAME
                    ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();
}

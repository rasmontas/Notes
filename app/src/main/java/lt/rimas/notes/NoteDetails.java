package lt.rimas.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import lt.rimas.notes.databinding.ActivityNoteDetailsBinding;
import lt.rimas.notes.repository.MainDataBase;
import lt.rimas.notes.repository.NoteDao;

public class NoteDetails extends BaseActivity {

    private Note note;
    private ActivityNoteDetailsBinding binding;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String demoResult;
    private final String SAVE_INSTANCE_KEY = "note_details_save_instance_key";
    private NoteDao noteDao;

    public NoteDetails() {
        super("NoteDetails", "tst_lfc_Note_Details_Activity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteDao = MainDataBase.getInstance(getApplicationContext()).noteDao();

        Intent intent = getIntent();

//        int id = intent.getIntExtra("id", 0);
//        String title = intent.getStringExtra("title");
//        String description = intent.getStringExtra("description");
//
//        binding.textView.setText(
//                id + "\n" + "Title: " + title + "\n" + description
//        );
        if (intent.getExtras() != null) {
           int noteId = intent.getIntExtra("noteId", 0);
            note = noteDao.getById(noteId);
            if (note == null) note = new Note();
        } else {
            note = new Note();
        }

        displayNoteDetails();
        setUpSaveButton();

        print("demoResult: " + demoResult);

        binding.noteNameEditText.setOnFocusChangeListener(
                (view, b) -> {
                    demoResult = binding.noteNameEditText.getText().toString();
                    print("demoResult: " + demoResult);
                }
        );


    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        demoResult = savedInstanceState.getString(SAVE_INSTANCE_KEY);
        print("OnRestore: " + demoResult);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SAVE_INSTANCE_KEY, demoResult);
    }

    private void displayNoteDetails() {
        binding.noteIdTextView.setText(String.valueOf(note.getId()));
        binding.noteNameEditText.setText(note.getTitle());
        binding.noteContentEditText.setText(note.getDescription());
        binding.noteCreationDateTextView.setText(
                note.getCreationDate() != null ? note.getCreationDate().format(formatter) : "no data"
        );
        binding.noteUpdateDateTextView.setText(
                note.getUpdateDate() != null ? note.getUpdateDate().format(formatter) : "no data"
        );
    }


    private void setUpSaveButton() {
        binding.saveButton.setOnClickListener(
                v -> {
                    saveNewNote();
                    finish();
                });
    }

    private void saveNewNote() {
        note.setTitle(
                binding.noteNameEditText.toString()
        );
        note.setDescription(
                binding.noteContentEditText.getText().toString()
        );

        noteDao.insertNote(note);
    }
}
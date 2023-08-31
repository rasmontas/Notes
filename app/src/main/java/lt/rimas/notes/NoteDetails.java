package lt.rimas.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.time.format.DateTimeFormatter;

import lt.rimas.notes.databinding.ActivityNoteDetailsBinding;

public class NoteDetails extends AppCompatActivity {

    private ActivityNoteDetailsBinding binding;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
//        int id = intent.getIntExtra("id", 0);
//        String title = intent.getStringExtra("title");
//        String description = intent.getStringExtra("description");
//
//        binding.textView.setText(
//                id + "\n" + "Title: " + title + "\n" + description
//        );
        if (intent.getExtras() != null) {
            note = intent.getParcelableExtra("note");
            displayNoteDetails(note);
        }

        setUpSaveButtonClick();
    }

    private void displayNoteDetails(Note note) {
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

    private void setUpSaveButtonClick() {
        binding.saveButton.setOnClickListener(
                v -> {

                    Intent finishIntent = new Intent();
                    finishIntent.putExtra("note_object_return", note);
                    setResult(RESULT_OK, finishIntent);
                    finish();
                }
        );
    }
}
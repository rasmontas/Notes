package lt.rimas.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import lt.rimas.notes.databinding.ActivityNoteDetailsBinding;

public class NoteDetails extends AppCompatActivity {

    private Note note;
    private ActivityNoteDetailsBinding binding;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int noteId = 0;
//        int id = intent.getIntExtra("id", 0);
//        String title = intent.getStringExtra("title");
//        String description = intent.getStringExtra("description");
//
//        binding.textView.setText(
//                id + "\n" + "Title: " + title + "\n" + description
//        );
        if (intent.getExtras() != null) {
            noteId = intent.getIntExtra("noteId", 0);

        }
        displayNoteDetails(noteId);
        setUpSaveButton();
    }

    private void displayNoteDetails(int noteId) {

        if (noteId == 0) {
            note = new Note();

        } else {
            getNoteFromRepository(noteId);

        }
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

    private void getNoteFromRepository(int noteId) {
        note = UseCaseRepository.notes.stream()
                .filter(note -> note.getId() == noteId)
                .findFirst()
                .get();
    }

    private void setUpSaveButton() {
        binding.saveButton.setOnClickListener(
                v -> {

                    addValuesToNote();

                    if (note.getId() == 0) {
                        saveNewNote();
                    } else {
                        updateNote();
                    }
                    finish();
                }
        );
    }

    private void addValuesToNote() {
        note.setTitle(
                binding.noteNameEditText.toString()
        );
        note.setDescription(
                binding.noteContentEditText.getText().toString()
        );
    }

    private void saveNewNote() {

        int maxId = UseCaseRepository.notes.stream()
                .max(Comparator.comparing(Note::getId))
                .get()
                .getId();

        UseCaseRepository.notes.add(
                new Note(
                        maxId + 1,
                        note.getTitle(),
                        note.getDescription()
                )
        );
    }

    private void updateNote() {
        Note newNote = UseCaseRepository.notes.stream()
                .filter(oldNote -> oldNote.getId() == note.getId())
                .findFirst().get();

        newNote.setTitle(note.getTitle());
        newNote.setDescription(note.getDescription());
    }
}
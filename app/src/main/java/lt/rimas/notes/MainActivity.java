package lt.rimas.notes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;

import lt.rimas.notes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_notes_main_activity";
    private ActivityMainBinding binding;
    private ArrayAdapter<Note> adapter;
    private ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpListView();
        setUpListViewItemClick();
        setUpListViewItemLongClick();
        setUpFloatingActionButtonClick();
    }

    private void setUpListView() {
        notes = new ArrayList<>();

        notes.addAll(
                UseCaseRepository.generateDummyNotes(25)
        );

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );

        binding.notesListView.setAdapter(adapter);
    }

    private void setUpListViewItemClick() {
        binding.notesListView.setOnItemClickListener(
                (adapterView, view, position, l) -> {

//                    Log.i(TAG, "OnListItemClicked : " + adapterView.getItemAtPosition(position));
//                    Log.i(TAG, "OnListItemClicked : " + position);
                    Note note = (Note) adapterView.getItemAtPosition(position);
                    openNoteDetailsActivity(note);
                }
        );
    }

    private void setUpListViewItemLongClick() {
        binding.notesListView.setOnItemLongClickListener(
                (adapterView, view, position, l) -> {
                    Note note = (Note) adapterView.getItemAtPosition(position);
                    showRemoveAllertDialog(note);
                    return true;
                }
        );
    }

    private void setUpFloatingActionButtonClick() {
        binding.floatingActionButton.setOnClickListener(
                view -> {
                    openNoteDetailsActivity(new Note());
                }
        );
    }

    private void showRemoveAllertDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.
                setMessage("Do you really want to remove this item?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    removeNoteFromList(note);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showSnackbar(String message) {
        Snackbar
                .make(
                        binding.notesListView,
                        message,
                        Snackbar.LENGTH_LONG
                )
                .show();
    }

    private void openNoteDetailsActivity(Note note) {


//        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, note.toString());  ---SEND
//        sendIntent.setType("text/plain");
//
//        Intent shareIntent = Intent.createChooser(sendIntent, null);
//
//        startActivity(shareIntent);
//        String videoId = "0xB3T4MPEr0";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
//        intent.putExtra("VIDEO_ID", videoId);
//        startActivity(intent);                        -------YOUTUBE
        Intent intent = new Intent(this, NoteDetails.class);
//        intent.putExtra("id", note.getId());
//        intent.putExtra("title", note.getTitle());
//        intent.putExtra("description", note.getDescription());
//        intent.putExtra("creation", note.getCreationDate());
//        intent.putExtra("updateDate", note.getUpdateDate());
        intent.putExtra("note", note);
//        startActivity(intent);
        startActivityForReturn.launch(intent);

    }

    private void removeNoteFromList(Note note) {
        notes.remove(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was removed");
    }

    private void addNoteToList(Note note) {
        notes.add(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was add");
    }

    private void updateNoteInList(Note note) {
        Note newNote = notes.stream()
                .filter(oldNote -> oldNote.getId() == note.getId())
                .findFirst().get();
        newNote.setTitle(note.getTitle());
        newNote.setDescription(note.getDescription());
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was updated");
    }

    ActivityResultLauncher<Intent> startActivityForReturn = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Note note = (Note) data.getParcelableExtra("note_object_return");
                        if (note.getId() == 0) {
                            int maxId = notes
                                    .stream().max(Comparator.comparing(Note::getId)).get().getId();
                            Note newNote = new Note(
                                    maxId + 1,
                                    note.getTitle(),
                                    note.getDescription()
                            );
                            addNoteToList(newNote);
                        } else {
                            updateNoteInList(note);
                        }
                    }
                }
            }
    );
}
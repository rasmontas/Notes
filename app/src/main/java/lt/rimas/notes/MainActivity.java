package lt.rimas.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import lt.rimas.notes.databinding.ActivityMainBinding;
import lt.rimas.notes.repository.MainDataBase;
import lt.rimas.notes.repository.NoteDao;

public class MainActivity extends BaseActivity {

    private static final String TAG = "my_notes_main_activity";
    private ActivityMainBinding binding;
    private ArrayAdapter<Note> adapter;
    private List<Note> notes;
    private NoteDao noteDao;

    public MainActivity() {
        super("MainActivity", "tst_lfc_main_activity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setUpListViewItemClick();
        setUpListViewItemLongClick();
        setUpFloatingActionButtonClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpListView();
    }

    private void setUpListView() {

        noteDao = MainDataBase
                .getInstance(getApplicationContext())
                .noteDao();


        if (notes.isEmpty()) {
            UseCaseRepository.generateDummyNotes(25);
            noteDao.insertNotes(UseCaseRepository.notes);
        }

        notes = noteDao.getAll();
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
        intent.putExtra("noteId", note.getId());
        startActivity(intent);

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

    private void removeNoteFromList(Note note) {
        notes.remove(note);
        adapter.notifyDataSetChanged();
        showSnackbar("Note with id: " + note.getId() + " was removed");
    }
}
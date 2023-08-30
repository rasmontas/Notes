package lt.rimas.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UseCaseRepository {

    public static List<Note> generateDummyNotes(int notesNumber) {
        ArrayList<Note> notes = new ArrayList<>();

        for (int i = 1; i <= notesNumber; i++) {
            notes.add(
                    new Note(
                            i,
                            UUID.randomUUID().toString().substring(0, 10),
                            UUID.randomUUID().toString()
                    )
            );
        }

        return notes;
    }
}

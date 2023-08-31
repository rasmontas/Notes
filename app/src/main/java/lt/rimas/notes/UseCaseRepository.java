package lt.rimas.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UseCaseRepository {

    public static List<Note> notes = new ArrayList<>();
    public static void generateDummyNotes(int notesNumber) {

        for (int i = 1; i <= notesNumber; i++) {
            notes.add(
                    new Note(
                            i,
                            UUID.randomUUID().toString().substring(0, 10),
                            UUID.randomUUID().toString()
                    )
            );
        }
    }
}

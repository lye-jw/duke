package duke.command;

import duke.DukeException;
import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DoneCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void executeDone_noTaskNumber_exceptionThrown() {
        try {
            System.setOut(new PrintStream(outContent));
            new DoneCommand("", new String[]{"done"})
                    .execute(new TaskList(), new Ui(null, null), new Storage("", null));
            fail();
        } catch (DukeException de) {
            String correctExpected = ":( OOPS!!! Please specify number of a single task to mark as done.\n";
            assertEquals(correctExpected, de.getMessage());
        }
    }

    @Test
    public void executeDone_intBeyondList_exceptionThrown() {
        try {
            System.setOut(new PrintStream(outContent));
            new DoneCommand("", new String[]{"done", "100"})
                    .execute(new TaskList(), new Ui(null, null), new Storage("", null));
            fail();
        } catch (DukeException de) {
            String correctExpected = ":( OOPS!!! Please specify valid task number.\n";
            assertEquals(correctExpected, de.getMessage());
        }
    }
}

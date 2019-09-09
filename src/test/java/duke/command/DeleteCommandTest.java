package duke.command;

import duke.DukeException;
import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void executeDelete_noTaskNumber_exceptionThrown() {
        try {
            System.setOut(new PrintStream(outContent));
            new DeleteCommand("", new String[]{"delete"})
                    .execute(new TaskList(), new Ui(null, null), new Storage("", null));
            fail();
        } catch (DukeException de) {
            String correctExpected = ":( OOPS!!! Please specify number of a single task to delete.\n";
            assertEquals(correctExpected, de.getMessage());
        }
    }

    @Test
    public void executeDelete_intBeyondList_exceptionThrown() {
        try {
            System.setOut(new PrintStream(outContent));
            new DeleteCommand("", new String[]{"delete", "100"})
                    .execute(new TaskList(), new Ui(null, null), new Storage("", null));
            fail();
        } catch (DukeException de) {
            String correctExpected = ":( OOPS!!! Please specify valid task number.\n";
            assertEquals(correctExpected, de.getMessage());
        }
    }

    @Test
    public void testIsExitValue() {
        assertFalse(new DeleteCommand("", null).isExit());
    }
}

package duke.command;

import duke.DukeException;
import duke.helper.Parser;
import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.task.Todo;

import java.time.LocalDateTime;

/**
 * Adds a Task of type Todo, Deadline or Event to the list.
 */
public class AddCommand extends Command {
    private String addType;
    private String userInput;

    /**
     * Initializes instance of AddCommand.
     *
     * @param addType Type of Task to be added.
     * @param userInput Input entered by the user.
     * @param inputSplit String array of user input split by spaces.
     * @param filePath Path of save file on hard disk to be written to.
     */
    public AddCommand(String addType, String userInput, String[] inputSplit, String filePath) {
        super(filePath, inputSplit);
        this.addType = addType;
        this.userInput = userInput;
    }

    /**
     * Adds Task of type Todo, Deadline or Event to the list.
     *
     * @param tasks List of Tasks to be added to.
     * @param ui Ui class that handles printing to user interface.
     * @param storage Storage class that handles writing to save file on hard disk.
     * @throws DukeException If input has invalid format.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        switch (addType) {
        case "todo":
            if (inputSplit.length == 1) {
                // Exception if no description after "todo"
                throw new DukeException(":( OOPS!!! The description of a todo cannot be empty.\n");
            }
            Todo todo = new Todo(userInput.replace("todo ", ""), 0);
            tasks.addToList(todo);
            String writeStringT = todo.getType() + " " + 0 + " " + todo.getDescription() + "\n";
            storage.writeToFile(filePath, writeStringT, true);
            ui.printAddNotification(todo.toString(), tasks.getSize());
            break;
        case "deadline":
            if (!userInput.contains(" /by ")) {
                // Exception for invalid deadline format
                throw new DukeException(":( OOPS!!! For deadline please use the format\n"
                        + "\"deadline description /by end time\"\n");
            }
            String[] splitStringD = userInput.split(" /by ");
            LocalDateTime inputDateD = Parser.convertToDate(splitStringD[1], Parser.dateFormats);
            String inputDateStrD = inputDateD == null ? splitStringD[1]
                    : inputDateD.format(Parser.OUTPUT_FORMAT);
            Deadline deadline = new Deadline(splitStringD[0].replace("deadline ", ""), 0,
                    inputDateStrD);
            tasks.addToList(deadline);
            String writeStringD = deadline.getType() + " 0" + " " + deadline.getDescription() + " | "
                    + deadline.getEndTime() + "\n";
            storage.writeToFile(filePath, writeStringD, true);
            ui.printAddNotification(deadline.toString(), tasks.getSize());
            break;
        case "event":
            if (!userInput.contains(" /at ")) {
                // Exception for invalid deadline format
                throw new DukeException(":( OOPS!!! For event please use the format\n"
                        + "\"event description /at period\"\n");
            }
            String[] splitStringE = userInput.split(" /at ");
            LocalDateTime inputDateE = Parser.convertToDate(splitStringE[1], Parser.dateFormats);
            String inputDateStrE = inputDateE == null ? splitStringE[1]
                    : inputDateE.format(Parser.OUTPUT_FORMAT);
            Event event = new Event(splitStringE[0].replace("event ", ""), 0,
                    inputDateStrE);
            tasks.addToList(event);
            String writeStringE = event.getType() + " 0" + " " + event.getDescription() + " | " + event.getEventPeriod()
                    + "\n";
            storage.writeToFile(filePath, writeStringE, true);
            ui.printAddNotification(event.toString(), tasks.getSize());
            break;
        default:
            assert false : "Invalid item in external save file";
            // throw new AssertionError("Invalid item in external save file");
        }
    }
}

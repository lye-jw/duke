package duke.dukeCommand;

import duke.DukeException;
import duke.dukeHelper.*;
import duke.dukeTask.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommand extends Command {
    private String addType;
    private String userInput;

    public AddCommand(String addType, String userInput, String[] inputSplit, String filePath) {
        super(filePath, inputSplit);
        this.addType = addType;
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        switch (addType) {
        case "todo":
            if (inputSplit.length == 1) {
                // Exception if no description after "todo"
                throw new DukeException(ui.separationLine
                        + "\n     \u2639 OOPS!!! The description of a todo cannot be empty.\n"
                        + ui.separationLine + "\n");
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
                throw new DukeException(ui.separationLine + "\n     \u2639 OOPS!!! For deadline please use the format\n"
                        + "               \"deadline description /by end time\"\n" + ui.separationLine + "\n");
            }
            String[] splitStringD = userInput.split(" /by ");
            Date inputDateD = Parser.convertToDate(splitStringD[1], Parser.dateFormats);
            String inputDateStrD = inputDateD == null ? splitStringD[1]
                    : new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(inputDateD);
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
                throw new DukeException(ui.separationLine + "\n     \u2639 OOPS!!! For event please use the format\n"
                        + "               \"event description /at period\"\n" + ui.separationLine + "\n");
            }
            String[] splitStringE = userInput.split(" /at ");
            Date inputDateE = Parser.convertToDate(splitStringE[1], Parser.dateFormats);
            String inputDateStrE = inputDateE == null ? splitStringE[1]
                    : new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(inputDateE);
            Event event = new Event(splitStringE[0].replace("event ", ""), 0,
                    inputDateStrE);
            tasks.addToList(event);
            String writeStringE = event.getType() + " 0" + " " + event.getDescription() + " | " + event.getEventPeriod()
                    + "\n";
            storage.writeToFile(filePath, writeStringE, true);
            ui.printAddNotification(event.toString(), tasks.getSize());
            break;
        default:
            break;
        }
    }
}

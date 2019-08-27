package duke.command;

import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.TaskList;

/**
 * Displays all existing Tasks in the list.
 */
public class ListCommand extends Command {

    public ListCommand(String filePath, String[] inputSplit) {
        super(filePath, inputSplit);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printTaskList(tasks.toArrayList());
    }
}
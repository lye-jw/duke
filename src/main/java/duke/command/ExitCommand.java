package duke.command;

import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.TaskList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExitCommand extends Command {

    public ExitCommand(String filePath, String[] inputSplit) {
        super(filePath, inputSplit);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
    }

    /**
     * Terminates the program.
     */
    public static void exit() {
        // Thread.sleep() and Timeout.SECONDS.sleep() don't work as System shuts down without printing exit message.
        // Use ScheduledExecutorService to make System exit after a delay, after exit message is printed.
        ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        // schedule() takes Runnable, delay, unit
        exe.schedule(command, 2, TimeUnit.SECONDS);
        exe.shutdown();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

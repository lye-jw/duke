package duke;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.helper.Parser;
import duke.helper.Storage;
import duke.helper.Ui;
import duke.task.TaskList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Duke extends Application {

    private String filePath;
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    /**
     * Initializes Duke object.
     */
    public Duke() {
        this.filePath = new File("").getAbsolutePath() + "/data/duke.txt";
        ui = new Ui(null, userInput);
        storage = new Storage(filePath, null);
        Parser.setFilePath(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set up display window components
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(400, 560);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer = new VBox();
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        userInput.setPrefWidth(335.0);
        userInput.setPrefHeight(35.0);

        sendButton = new Button("Send");
        sendButton.setPrefWidth(60.0);
        sendButton.setPrefHeight(35.0);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.setPrefSize(400.0, 600.0);
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Create Stage
        scene = new Scene(mainLayout);
        primaryStage.setTitle("Duke");
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(600.0);
        primaryStage.setMinWidth(400.0);
        primaryStage.setScene(scene);
        primaryStage.show();

        ui = new Ui(dialogContainer, userInput);
        storage = new Storage(filePath, dialogContainer);
        ui.showWelcome();

        // Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });
    }

    /**
     * Handles reading of input and input and producing of output.
     */
    private void handleUserInput() {
        boolean isExit = false;
        try {
            String fullCommand = ui.readInput();
            ui.printUserInput();
            Command c = Parser.parse(fullCommand);
            c.execute(tasks, ui, storage);
            isExit = c.isExit();
        } catch (DukeException de) {
            ui.printError(de.getMessage());
        }
        if (isExit) {
            ui.printExitMessage();
            ExitCommand.exit();
        }
    }
}

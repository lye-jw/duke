package duke.fxgui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Initializes a dialog box in the GUI.
     *
     * @param text Text to be displayed in Label.
     * @param img Image to be displayed in ImageView.
     * @param type Indication of whether dialog box is for "user" or "duke" dialog.
     */
    public DialogBox(String text, Image img, String type) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Circle clipping starts from top left corner of image
        displayPicture.setClip(new Circle(50, 50, 50));

        if ("user".equals(type)) {
            this.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, new CornerRadii(20.0),
                    new Insets(5, 5, 5, 5))));
        } else {
            this.setBackground(new Background(new BackgroundFill(Color.KHAKI, new CornerRadii(20.0),
                    new Insets(5, 5, 5, 5))));
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, "user");
    }

    /**
     * Produces a duke dialog box that is flipped to the left side of the interface.
     *
     * @param text Text contained by the dialog box.
     * @param img Image representing duke.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img, "duke");
        db.flip();
        assert db.getAlignment() == Pos.TOP_LEFT : "Wrong alignment for Duke Dialog box";
        return db;
    }
}

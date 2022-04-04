package hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.*;
import javafx.scene.paint.*;


public class Controller {

    @FXML
    private Rectangle myRect;

    //private Label label;

    public void initialize() {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");
        // label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        myRect = new Rectangle(25,25,250,250);
        myRect.setFill(Color.PINK);
    }
}
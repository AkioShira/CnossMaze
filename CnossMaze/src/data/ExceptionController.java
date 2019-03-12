package data;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Schischko A.A. on 19.11.2017.
 */
public class ExceptionController {
    private Stage exMessage;

    @FXML
    void errorStageClose(MouseEvent event) {
        exMessage.close();
    }

    public void setStage(Stage stage) {
        exMessage = stage;
    }
}

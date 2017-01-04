package sample;

/**
 * Created by piotr.walczak on 04.01.2017.
 */

import javafx.fxml.FXML;
import javafx.stage.Stage;


/**
 * Dialog to edit details of a person.
 *
 * @author Marco Jakob
 */
public class AddItemController {


    private Stage dialogStage;
    private boolean okClicked = false;
    private Main mainApp;

    @FXML
    private javafx.scene.control.TextField tf;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOK()
    {
        this.mainApp.getDownloadsList().add(new SingleDownloadEntryModel(tf.getText()));
        dialogStage.close();
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }

}

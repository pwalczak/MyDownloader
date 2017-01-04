package sample;

import engine.BackgroundMonitorThread;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private ObservableList<SingleDownloadEntryModel> downloadsList = FXCollections.observableArrayList();
    private BackgroundMonitorThread backgroundMonitor = BackgroundMonitorThread.getInstance();
    private Thread backgroundMonitorThread = new Thread(backgroundMonitor);

    public Main() {

    }

    @Override
    public void stop() {
        this.backgroundMonitor.close();
        backgroundMonitorThread.interrupt();
    }

    public BackgroundMonitorThread getBackgroundMonitor() {
        return backgroundMonitor;
    }

    @Override
    public void start(Stage primaryStage) {
        backgroundMonitorThread.start();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        try {
            initRootLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initRootLayout() throws IOException {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("mainDialog.fxml"));

            AnchorPane rootLayout = loader.load();

            MyDownloaderController controller = loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    public ObservableList<SingleDownloadEntryModel> getDownloadsList() {
        return downloadsList;
    }

    public void showPersonEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("newDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddItemController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

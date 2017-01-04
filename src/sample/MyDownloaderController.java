package sample;

/**
 * Created by piotr.walczak on 03.01.2017.
 */

import engine.Downloader;
import engine.DownloaderImp;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class MyDownloaderController {
    @FXML
    private TableView<SingleDownloadEntryModel> downloadTable;
    @FXML
    private TableColumn<SingleDownloadEntryModel, String> downloadNameColumn;

    @FXML
    private MenuItem newItem;

    // Reference to the main application.
    private sample.Main mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MyDownloaderController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        downloadNameColumn.setCellValueFactory(cellData -> cellData.getValue().downloadAddressProperty());
        newItem.setOnAction(event -> mainApp.showPersonEditDialog());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(sample.Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        downloadTable.setItems(mainApp.getDownloadsList());
    }


    public void startDownload(SingleDownloadEntryModel entry) {
        try {
            Downloader d = DownloaderImp.getDownloaderImpInstance(entry.getDownloadAddress());
            this.mainApp.getBackgroundMonitor().submitTask(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopDownload(SingleDownloadEntryModel entry) {
        try {
            Downloader d = DownloaderImp.getDownloaderImpInstance(entry.getDownloadAddress());
            this.mainApp.getBackgroundMonitor().cancelTask(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleStart() {
        if (!downloadTable.getSelectionModel().isEmpty())
            startDownload(downloadTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleStop() {
        if (!downloadTable.getSelectionModel().isEmpty())
            stopDownload(downloadTable.getSelectionModel().getSelectedItem());
    }
}

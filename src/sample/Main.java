package sample;

import engine.BackgroundMonitoring;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public BackgroundMonitoring getBackgroundMonitor() {
        return backgroundMonitor;
    }

    private BackgroundMonitoring backgroundMonitor = BackgroundMonitoring.getInstance();


    public Main() {
        // Add some sample data

    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("http://buildlogs.centos.org/rolling/7/isos/x86_64/CentOS-7-x86_64-DVD.iso", "Muster"));
        personData.add(new Person("http://mirror.centos.org/centos/7/updates/x86_64/Packages/systemd-219-30.el7_3.3.x86_64.rpm", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));

        try {
            initRootLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showPersonOverview();
    }



    public void initRootLayout() throws IOException {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("sample.fxml"));

            rootLayout = (AnchorPane) loader.load();

            PersonOverviewController controller = loader.getController();
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


    public ObservableList<Person> getPersonData() {
        return personData;
    }


    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
          //  rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

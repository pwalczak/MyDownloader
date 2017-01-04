package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by piotr.walczak on 04.01.2017.
 */
public class SingleDownloadEntryModel {
    private final StringProperty downloadAddress;

    public SingleDownloadEntryModel(String downloadAdress) {
        this.downloadAddress = new SimpleStringProperty(downloadAdress);
    }

    public String getDownloadAddress() {
        return downloadAddress.get();
    }

    public StringProperty downloadAddressProperty() {
        return downloadAddress;
    }


}

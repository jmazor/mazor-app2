package baseline;

import javafx.collections.ObservableList;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class InventoryList {
    // TODO I need to decide if this should be an Arraylist or an ObservableList
    private ObservableList<InventoryItem> dataList;
    // TODO do I want FILE to be a data member
    // we only have the Save-as option. No Save

    public InventoryList() {
        // set dataList to NULL
    }

    public InventoryList(File fileName) {

    }


    public void verifySerialNumber(InventoryItem inputItem) {
        // loop through dataList
        // if serialNumber matches another throw exception
        // else return
    }

    public void readData(File fileName) {
        // read each line and csv parse the data
        // call inputData
    }

    public void inputData(List<String> data) {
        // init count
        // for every item in the list
        // count % 3
        // create InventoryList
        // if count equals 1
        // set serialNumber
        // call verifySerialNumber
        // if count equals 2
        // set itemName
        // if count equals 0
        // on error throw exception
    }

    public ObservableList<InventoryItem> searchList(String serialNumber, String itemName, BigDecimal itemValue) {
        // create a new ObservableList
        // if SerialNumber is not blank
        // remove every item that does not contain serialNumber

        // if itemName is not blank
        // remove every item that does not contain itemName

        // if itemValue is not blank
        // convert to string???
        // this can be an exact match
        // this also is not a required search function

        // return new List
        return null;
    }

}

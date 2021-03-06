/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */
package baseline;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    InventoryList inventoryList;
    @FXML
    private TableView<InventoryItem> table;

    @FXML
    private TableColumn<InventoryItem, String> serialNumber;

    @FXML
    private TableColumn<InventoryItem, String> itemName;

    @FXML
    private TableColumn<InventoryItem, String> itemValue;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputSerial;

    @FXML
    private TextField inputValue;

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private MenuItem exportMenu;

    @FXML
    private MenuItem importMenu;

    @FXML
    private MenuItem clearMenu;


    @FXML
    public void handleAddButton() {
        try {
            // call add function
            inventoryList.add(inputSerial.getCharacters().toString(), inputName.getCharacters().toString(), inputValue.getCharacters().toString());
            inputSerial.clear();
            inputName.clear();
            inputValue.clear();
            // clear fields
        } catch (IllegalArgumentException e) {
            // popup error
            popupError(e.getMessage());
        }
        refreshItems();

    }

    @FXML
    public void handleSearchButton() {
        // call searchList
        table.setItems(inventoryList.searchList(inputSerial.getCharacters().toString(), inputName.getCharacters().toString(), inputValue.getCharacters().toString()));
        inputSerial.clear();
        inputName.clear();
        inputValue.clear();
        // clear fields
    }

    @FXML
    public void handleDeleteButton() {
        inventoryList.delete(table.getSelectionModel().getSelectedItem());
        // call delete
        refreshItems();
    }

    @FXML
    public void handleExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV", "*.txt"),
                new FileChooser.ExtensionFilter("Json", "*.json"),
                new FileChooser.ExtensionFilter("HTML", "*.html")
        );
        File file = fileChooser.showSaveDialog(table.getScene().getWindow());
        try {
            // ensure filename has proper extension
            if (file != null) {
                File f;
                String tempPath = file.getCanonicalPath().toLowerCase();
                if (!(tempPath.endsWith(".txt") || tempPath.endsWith(".json") || tempPath.endsWith(".html"))) {
                    String extension = fileChooser.selectedExtensionFilterProperty().get().getExtensions().get(0).substring(1);
                    f = new File(file.getCanonicalPath() + extension);
                } else {
                    f = file.getCanonicalFile();
                }
                // export list
                inventoryList.exportList(f);
            }
        } catch (Exception e) {
            popupError("File is Invalid or Corrupted");
        }
        refreshItems();
    }

    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All", "*"),
                new FileChooser.ExtensionFilter("TSV", "*.txt"),
                new FileChooser.ExtensionFilter("Json", "*.json"),
                new FileChooser.ExtensionFilter("HTML", "*.html")
        );
        File file = fileChooser.showOpenDialog(table.getScene().getWindow());
        if (file != null) {
            try {
                // call importFile
                inventoryList.importFile(file);
            } catch (Exception e) {
                inventoryList.clearList();
                popupError("File is Invalid or Corrupted");
            }
        }
        refreshItems();
    }

    @FXML
    public void handleClear() {
        // call clear list
        inventoryList.clearList();
        refreshItems();
        inputSerial.clear();
        inputName.clear();
        inputValue.clear();
        // clear text fields

    }

    private void setUpSerialNumber() {
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        serialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumber.setOnEditCommit(event -> {
            InventoryItem newItem = event.getRowValue();
            try {
                // call setSerial
                inventoryList.setSerialNumber(newItem, event.getNewValue());
            } catch (Exception e) {
                popupError(e.getMessage());
            }
            refreshItems();
        });
    }

    private void setUpItemName() {
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemName.setCellFactory(TextFieldTableCell.forTableColumn());
        itemName.setOnEditCommit(event -> {
            InventoryItem newItem = event.getRowValue();
            try {
                // call setItemName
                inventoryList.setItemName(newItem, event.getNewValue());
            } catch (Exception e) {
                popupError(e.getMessage());
            }
            refreshItems();
        });
    }

    private void setUpItemValue() {
        itemValue.setCellValueFactory(new PropertyValueFactory<>("itemValue"));
        itemValue.setCellFactory(TextFieldTableCell.forTableColumn());
        itemValue.setOnEditCommit(event -> {
            InventoryItem newItem = event.getRowValue();
            try {
                // call setItemValue
                inventoryList.setItemValue(newItem, event.getNewValue());
            } catch (Exception e) {
                popupError(e.getMessage());
            }
            refreshItems();
        });

        // Proper Sort comparator
        Comparator<String> itemComparator = (o1, o2) -> {
            String firstString = o1.substring(1);
            String secondString  = o2.substring(1);
            BigDecimal testOne = new BigDecimal(firstString);
            BigDecimal testTwo = new BigDecimal(secondString);
            return testOne.compareTo(testTwo);
        };
        itemValue.setComparator(itemComparator);

    }

    private void refreshItems() {
        // refresh table
        table.setItems(inventoryList.getDataList());
        table.refresh();
    }


    private void popupError(String message) {
        // Popup error
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        // pass exception string
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
        public void initialize(URL location, ResourceBundle resources) {
        inventoryList = new InventoryList();
        table.setEditable(true);
        // set up serial edits
        setUpSerialNumber();
        // set up name edits
        setUpItemName();
        // set up value edits
        setUpItemValue();
        table.setItems(inventoryList.getDataList());

    }
}

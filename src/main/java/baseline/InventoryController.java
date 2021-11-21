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
import java.io.IOException;
import java.net.URL;
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
            inventoryList.add(inputSerial.getCharacters().toString(), inputName.getCharacters().toString(), inputValue.getCharacters().toString());
            inputSerial.clear();
            inputName.clear();
            inputValue.clear();
        } catch (IllegalArgumentException e) {
            popupError(e.getMessage());
        } catch (OutOfMemoryError e) {
            popupError("List can not be greater than 2000 items");
        }
        refreshItems();

    }

    @FXML
    public void handleSearchButton() {
        table.setItems(inventoryList.searchList(inputSerial.getCharacters().toString(), inputName.getCharacters().toString(), inputValue.getCharacters().toString()));
        inputSerial.clear();
        inputName.clear();
        inputValue.clear();
    }

    @FXML
    public void handleDeleteButton() {
        inventoryList.delete(table.getSelectionModel().getSelectedItem());
        refreshItems();
    }

    @FXML
    public void handleExport() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TSV", "*.txt"),
                new FileChooser.ExtensionFilter("Json", "*.json"),
                new FileChooser.ExtensionFilter("HTML", "*.html")
        );
        File file = fileChooser.showSaveDialog(table.getScene().getWindow());
        try {
            if (file != null) {
                File f;
                String tempPath = file.getCanonicalPath().toLowerCase();
                if (!(tempPath.endsWith(".txt") || tempPath.endsWith(".json") || tempPath.endsWith(".html"))) {
                    String extension = fileChooser.selectedExtensionFilterProperty().get().getExtensions().get(0).substring(1);
                    f = new File(file.getCanonicalPath() + extension);
                } else {
                    f = file.getCanonicalFile();
                }
                inventoryList.exportList(f);
            }
        } catch (Exception e) {
            popupError("File is Invalid or Corrupted");
        }
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
                inventoryList.importFile(file);
            } catch (Exception e) {
                inventoryList.clearList();
                popupError("File is Invalid or Corrupted");
            }
        }
    }

    @FXML
    public void handleClear() {
        inventoryList.clearList();
        refreshItems();
        inputSerial.clear();
        inputName.clear();
        inputValue.clear();
    }

    private void setUpSerialNumber() {
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        serialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumber.setOnEditCommit(event -> {
            InventoryItem newItem = event.getRowValue();
            try {
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
                inventoryList.setItemValue(newItem, event.getNewValue());
            } catch (Exception e) {
                popupError(e.getMessage());
            }
            refreshItems();
        });
    }

    private void refreshItems() {
        table.setItems(inventoryList.getDataList());
        table.refresh();
    }

    private void popupError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
        public void initialize(URL location, ResourceBundle resources) {
        inventoryList = new InventoryList();
        table.setEditable(true);
        setUpSerialNumber();
        setUpItemName();
        setUpItemValue();
        table.setItems(inventoryList.getDataList());
    }
}

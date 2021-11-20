package baseline;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
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
    private MenuItem aboutMenu;

    @FXML
    public void handleInputSerial() {
    }

    @FXML
    public void handleInputName() {
    }

    @FXML
    public void handleInputValue() {
    }

    @FXML
    public void handleAddButton() {
    }

    @FXML
    public void handleSearchButton() {
    }

    @FXML
    public void handleDeleteButton() {
    }

    @FXML
    public void handleExport() {
    }

    @FXML
    public void handleImport() {
    }

    @FXML
    public void handleClear() {
    }

    @FXML
    public void handleAbout() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

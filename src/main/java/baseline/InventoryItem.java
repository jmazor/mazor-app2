package baseline;

import java.math.BigDecimal;

public class InventoryItem {
    private String serialNumber;
    private String itemName;
    private BigDecimal itemValue;

    public InventoryItem() {
        this.serialNumber = null;
        this.itemName = null;
        this.itemValue = null;
    }

    public InventoryItem(String serialNumber, String itemName, BigDecimal itemValue) {
        // call set serialNumber
        // cal get itemName
        // cal get BigDecimal
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        // we do not test for individuality here
        // a hypothetical situation would be the application has multiple lists and therefore serials could be duplicated
        // testing will be done in InventoryList
        this.serialNumber = serialNumber;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        // test if string is between 2 and 256 chars
        // throw IllegalArgumentException if not
        this.itemName = itemName;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        // Test to make sure it is in currency format
        // I have not decided how to deal with $ yet
        // It might be the default in the text field
        // it might be optional
        // it might not be allowed
        //  value should be not 0
        this.itemValue = itemValue;
    }

}

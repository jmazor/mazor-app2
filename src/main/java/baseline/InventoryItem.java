/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */
package baseline;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.util.Locale;

public class InventoryItem {
    @SerializedName(value = "serialNumber")
    private String serialNumber;
    @SerializedName(value = "itemName")
    private String itemName;
    @SerializedName(value = "itemValue")
    private String itemValue;

    public InventoryItem() {
        this.serialNumber = null;
        this.itemName = null;
        this.itemValue = null;
    }

    public InventoryItem(String serialNumber, String itemName, String itemValue) {
        setSerialNumber(serialNumber);
        setItemName(itemName);
        setItemValue(itemValue);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        // we do not test for individuality here
        // a hypothetical situation would be the application has multiple lists and therefore serials could be duplicated
        // testing will be done in InventoryList
        if (serialNumber.matches("[a-zA-Z]-\\w{3}-\\w{3}-\\w{3}"))
            this.serialNumber = serialNumber.toUpperCase(Locale.ROOT);
        else
            throw new IllegalArgumentException("Serial must in A-XXX-XXX-XXX format");


    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        // test if string is between 2 and 256 chars
        // throw IllegalArgumentException if not
        if (itemName.length()>256 || itemName.length()<2)
            throw new IllegalArgumentException("Name must be between 2 and 256 characters");
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        // Test to make sure it is in currency format
        // I have not decided how to deal with $ yet
        // It might be the default in the text field
        // it might be optional
        // it might not be allowed
        //  value should be not 0
        if (itemValue.isBlank())
            throw new IllegalArgumentException("Value must exist");
        // removes $ if exists
        if (itemValue.charAt(0) == '$') {
            itemValue = itemValue.substring(1);
        }
        BigDecimal temp;
        // ensures is a valid decimal
        try {
            temp = new BigDecimal(itemValue);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Value must be a valid number");
        }
        // ensures decimal is greater than or equal to zero
        if (temp.compareTo((BigDecimal.ZERO)) < 0)
            throw new IllegalArgumentException("Value must be greater than or equal to zero");
        this.itemValue = formatItemValue(itemValue);
    }

    private static String formatItemValue(String itemValue) {
        // adds $ back
        itemValue = "$".concat(itemValue);
        // finds index of decimal
        int index = itemValue.indexOf('.');
        // if decimal exists
        if (index != -1) {
            // if no value after decimal
            if (itemValue.length() == index + 1)
                itemValue = itemValue.concat("00");
            // if one decimal
            else if (itemValue.length() == index + 2)
                itemValue = itemValue.concat("0");
            // if more than 1 decimal
            else {
                itemValue = itemValue.substring(0, index + 3);
            }
        } else {  // if decimal doesn't exist
            itemValue = itemValue.concat(".00");
        }
        return itemValue;
    }

    public boolean compare(InventoryItem item) {
        boolean flag = true;
        if (!this.getSerialNumber().equals(item.getSerialNumber()))
            flag  = false;
        if (!this.getItemValue().equals(item.getItemValue()))
            flag = false;
        if (!this.getItemName().equals(item.getItemName()))
            flag = false;

        return flag;
    }

}

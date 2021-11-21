/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */

package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class InventoryList {
    // Data
    private ObservableList<InventoryItem> dataList;

    public InventoryList() {
        // set dataList to NULL
        dataList = FXCollections.observableArrayList();
    }


    public void add(String serialNumber, String itemName, String itemValue) {
        if (dataList.size() > 2000)
            throw new OutOfMemoryError();
        verifySerialNumber(serialNumber);
        dataList.add(new InventoryItem(serialNumber, itemName, itemValue));
    }

    public void setSerialNumber(InventoryItem inputItem, String serialNumber) {
        verifySerialNumber(serialNumber);
        inputItem.setSerialNumber(serialNumber);

    }

    public void setItemName(InventoryItem inputItem, String itemName) {
        inputItem.setItemName(itemName);
    }

    public void verifySerialNumber(String inputSerial) {
        // loop through dataList
        // if serialNumber matches another throw exception
        // else return
        for (InventoryItem item: dataList) {
            if (item.getSerialNumber().toLowerCase(Locale.ROOT).equals(inputSerial.toLowerCase(Locale.ROOT)))
                throw new IllegalArgumentException("Serial Number must be unique");
        }
    }

    public void setItemValue(InventoryItem inputItem, String itemValue) {
        inputItem.setItemValue(itemValue);
    }

    public ObservableList<InventoryItem> searchList(String serialNumber, String itemName, String itemValue) {
        // create a new ObservableList
        // if SerialNumber is not blank
        // remove every item that does not contain serialNumber
        ObservableList<InventoryItem> searchList = FXCollections.observableArrayList(getDataList());

        // if itemName is not blank
        // remove every item that does not contain itemName
        if(!serialNumber.isBlank()) {
            searchList.removeIf(searchItem -> !searchItem.getSerialNumber().toLowerCase(Locale.ROOT).contains(serialNumber.toLowerCase(Locale.ROOT)));
        }
        if(!itemName.isBlank()) {
            searchList.removeIf(searchItem -> !searchItem.getItemName().toLowerCase(Locale.ROOT).contains(itemName.toLowerCase(Locale.ROOT)));
        }

        if (!itemName.isBlank()) {
            searchList.removeIf(searchItem -> !searchItem.getItemValue().contains(itemValue));
        }

        // if itemValue is not blank
        // convert to string???
        // this can be an exact match
        // this also is not a required search function

        // return new List
        return searchList;
    }

    public void clearList() {
        dataList.clear();
    }

    public ObservableList<InventoryItem> getDataList() {
        return dataList;
    }

    public void delete(InventoryItem item) {
        dataList.remove(item);
    }

    // TODO fix getCanocialPath
    public void exportList(File fileName) throws IOException {
        String tempPath = fileName.getCanonicalPath().toLowerCase();
        if (tempPath.endsWith(".txt"))
            exportTSV(fileName);
        else if (tempPath.endsWith(".json"))
            exportJson(fileName);
        else
            throw new IOException();

    }

    private void exportTSV(File fileName){
        try (FileWriter myWriter = new FileWriter(fileName)) {
            for (InventoryItem item : dataList) {
                myWriter.write(item.getSerialNumber() + "\t");
                myWriter.write(item.getItemName() + "\t");
                myWriter.write(item.getItemValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFile(File fileName) throws IOException {
        String tempPath = fileName.getCanonicalPath().toLowerCase();
        clearList();
        if (tempPath.endsWith(".txt"))
            importTSV(fileName);
        if (tempPath.endsWith(".json"))
            importJson(fileName);
        else
            throw new IOException();
    }

    private void importJson(File fileName) throws IOException {
        Gson gson = new Gson();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileName))) {
            String input = "";
            String dataRow = jsonFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                dataRow = jsonFile.readLine();
            }
            Type listType = new TypeToken<ArrayList<InventoryItem>>(){}.getType();
            ArrayList<InventoryItem> tempList = gson.fromJson(input, listType);
            for (InventoryItem item : tempList) {
                // JSON does not use my constructor so we will temporarily have duplicates
                add(item.getSerialNumber(), item.getItemName(), item.getItemValue());
            }


        } catch (Exception e) {
            throw new IOException();
        }


    }

    private void importTSV(File fileName) throws IOException {
        StringTokenizer st ;
        try (BufferedReader tsvFile = new BufferedReader(new FileReader(fileName))) {
            String dataRow = tsvFile.readLine(); // Read first line.

            while (dataRow != null) {
                st = new StringTokenizer(dataRow, "\t");
                List<String> dataArray = new ArrayList<>();
                while (st.hasMoreElements()) {
                    dataArray.add(st.nextElement().toString());
                }
                add(dataArray.get(0), dataArray.get(1), dataArray.get(2));
                dataRow = tsvFile.readLine(); // Read next line of data.
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private void exportJson(File fileName) throws IOException {
        Gson gson = new Gson();
        ArrayList<InventoryItem> tempList = new ArrayList<>(dataList);
        try (Writer writer = new FileWriter(fileName)) {
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(tempList, writer);
        }
    }

}


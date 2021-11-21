/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */

package baseline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        // confirm list size
        if (dataList.size() > 2000)
            throw new OutOfMemoryError();
        // verify serial
        verifySerialNumber(serialNumber);
        // add new input
        dataList.add(new InventoryItem(serialNumber, itemName, itemValue));
    }

    public void setSerialNumber(InventoryItem inputItem, String serialNumber) {
        // verify serial before input
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
    // determines file type and calls appropriate function
    public void exportList(File fileName) throws IOException {
        String tempPath = fileName.getCanonicalPath().toLowerCase();
        if (tempPath.endsWith(".txt"))
            exportTSV(fileName);
        else if (tempPath.endsWith(".json"))
            exportJson(fileName);
        else if (tempPath.endsWith(".html"))
            exportHTML(fileName);
        else
            throw new IOException();

    }

    private void exportHTML(File fileName) throws IOException {
        try (FileWriter myWriter = new FileWriter(fileName)) {
            String output = ("""
                    <html>
                    <head>
                    <style>
                    table, th, td {
                      border:1px solid black;
                    }
                    </style>
                    </head>
                    <body>
                    """);
            output = output.concat("<table>\n<tbody>\n<tr>\n<th>Serial Number</th>\n<th>Item Name</td>\n<th>Value</td>\n</tr>\n");
            for (InventoryItem item : dataList) {
                // writes each item with a table
                output = output.concat("<tr>\n<td>" + item.getSerialNumber() + "</td>\n<td>" + item.getItemName()+ "</td>\n<td>" + item.getItemValue() + "</td>\n</tr>\n");

            }
            output = output.concat("</tbody>\n</table>\n");
            output = output.concat("</body>\n</html>\n");
            Document doc = Jsoup.parse(output);
            myWriter.write(String.valueOf(doc));
        } catch (Exception e) {
            throw new IOException();
        }

    }

    private void exportTSV(File fileName) throws IOException {
        // write to a file
        try (FileWriter myWriter = new FileWriter(fileName)) {
            for (InventoryItem item : dataList) {
                // writes each item with a table
                myWriter.write(item.getSerialNumber() + "\t");
                myWriter.write(item.getItemName() + "\t");
                myWriter.write(item.getItemValue() + "\n");
            }

        }
    }

    public void importFile(File fileName) throws IOException {
        // determines filetype
        String tempPath = fileName.getCanonicalPath().toLowerCase();
        clearList();
        // call appropriate function
        if (tempPath.endsWith(".txt"))
            importTSV(fileName);
        else if (tempPath.endsWith(".json"))
            importJson(fileName);
        else if(tempPath.endsWith(".html"))
            importHTML(fileName);
        else
            throw new IOException();
    }

    private void importJson(File fileName) throws IOException {
        Gson gson = new Gson();

        try (BufferedReader jsonFile = new BufferedReader(new FileReader(fileName))) {
            String input = "";
            // reads file and stores as a string
            String dataRow = jsonFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                dataRow = jsonFile.readLine();
            }
            Type listType = new TypeToken<ArrayList<InventoryItem>>(){}.getType();
            // deserialize json
            ArrayList<InventoryItem> tempList = gson.fromJson(input, listType);
            // verify data and put in dataList
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

    private void importHTML(File fileName) throws IOException {
        try (BufferedReader htmlFile = new BufferedReader(new FileReader(fileName))) {
            String input = "";
            // reads file and stores as a string
            String dataRow = htmlFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                dataRow = htmlFile.readLine();
            }
            Document doc = Jsoup.parse(input);
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); ++i) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                add(cols.get(0).text(), cols.get(1).text(), cols.get(2).text());
            }
        }


    }

}


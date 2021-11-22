/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */
package baseline;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class InventoryListTest {

    @Test
    void add() {
        InventoryList testList = new InventoryList();
        InventoryItem item = new InventoryItem("a-123-123-123", "test", "12");
        testList.add("a-123-123-123", "test", "12");
        assertTrue(testList.getDataList().get(0).compare(item));
    }

    @Test
    void addFailure() {
        InventoryList testList = new InventoryList();
        File file = new File("./src/test/data/SizeLimit.txt");
        // import a file with 2000 entries
        try {
            testList.importFile(file);
        } catch (Exception e) {
            fail();
        }
        // try and add a new entry
        try {
            testList.add("a-123-123-123", "test", "14");
            // if allows fail
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void getDataList() {
        InventoryList testList = new InventoryList();
        testList.add("b-123-123-123", "test", "14");
        if (testList.getDataList().size() != 1)
            fail();
        assertTrue(testList.getDataList().get(0).compare(new InventoryItem("B-123-123-123", "test", "14")));
    }


    @Test
    void setSerialNumberFailure() {
        String serialNum = "a-123-123-123";
        InventoryList testList = new InventoryList();
        testList.add(serialNum, "test", "12");
        testList.add("b-123-123-123", "test", "14");

        // should fail if serial is valid
        try {
            testList.setSerialNumber(testList.getDataList().get(1), "a-123-123-123");
            fail();
        } catch (Exception e) {
            // success if serial is invalid
            assertTrue(true);
        }
    }

    @Test
    void setSerialNumberSuccess() {
        // ensure serial is in proper format
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "14");
        String serialNum = "x-213-d32-3ds";
        testList.setSerialNumber(testList.getDataList().get(1), serialNum);
        assertEquals(testList.getDataList().get(1).getSerialNumber(), serialNum.toUpperCase(Locale.ROOT));
    }

    @Test
    void setItemName() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        // we have already ensured the name size in InventoryItemTest
        testList.setItemName(testList.getDataList().get(0), "new");
        assertEquals("new", testList.getDataList().get(0).getItemName());
    }


    @Test
    void setItemValue() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.setItemValue(testList.getDataList().get(0), "14.1");
        assertEquals("$14.10", testList.getDataList().get(0).getItemValue());
    }

    @Test
    void searchList() {
        // test search list
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "12");
        ObservableList<InventoryItem> searchList = testList.searchList("b", "", "");
        // make sure search results match
        if (searchList.size() > 1)
            fail();
        else {
            InventoryItem compareItem = new InventoryItem("b-123-123-123", "test", "12.");
            assertTrue(searchList.get(0).compare(compareItem));
        }
    }

    @Test
    void clearList() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "12");
        testList.clearList();
        // list should be empty
        assertEquals(0, testList.getDataList().size());
    }

    @Test
    void delete() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "12");
        testList.delete(testList.getDataList().get(0));
        if (testList.getDataList().size() != 1)
            fail();
        else {
            InventoryItem compareItem = new InventoryItem("b-123-123-123", "test", "12.");
            assertTrue(testList.getDataList().get(0).compare(compareItem));
        }
    }

    @Test
    void exportListTSV() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("c-123-123-123", "junk", "12");
        File file = new File("./src/test/data/TempTSV.txt");
        try {
            testList.exportList(file);
        } catch (Exception e) {
            fail();
        }
        String input = "";
        try (BufferedReader htmlFile = new BufferedReader(new FileReader(file))) {
            // reads file and stores as a string
            String dataRow = htmlFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                input = input.concat("\n");
                dataRow = htmlFile.readLine();
            }
        } catch (Exception e) {
            fail();
        }
        assertEquals("""
                A-123-123-123\ttest\t$12.00
                C-123-123-123\tjunk\t$12.00
                """, input);

        file.delete();
    }

    @Test
    void exportListHTML() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "12");
        File file = new File("./src/test/data/TempHTML.html");
        try {
            testList.exportList(file);
        } catch (Exception e) {
            fail();
        }
        String input = "";
        try (BufferedReader htmlFile = new BufferedReader(new FileReader(file))) {
            // reads file and stores as a string
            String dataRow = htmlFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                input = input.concat("\n");
                dataRow = htmlFile.readLine();
            }
        } catch (Exception e) {
            fail();
        }
        assertEquals("""
                <html>\s
                 <head>\s
                  <style>
                table, th, td {
                  border:1px solid black;
                }
                </style>\s
                 </head>\s
                 <body>\s
                  <table>\s
                   <tbody>\s
                    <tr>\s
                     <th>Serial Number</th>\s
                     <th>Item Name </th>
                     <th>Value </th>
                    </tr>\s
                    <tr>\s
                     <td>A-123-123-123</td>\s
                     <td>test</td>\s
                     <td>$12.00</td>\s
                    </tr>\s
                    <tr>\s
                     <td>B-123-123-123</td>\s
                     <td>test</td>\s
                     <td>$12.00</td>\s
                    </tr>\s
                   </tbody>\s
                  </table> \s
                 </body>
                </html>
                """, input);

        file.delete();
    }

    @Test
    void exportListJson() {
        InventoryList testList = new InventoryList();
        testList.add("a-123-123-123", "test", "12");
        testList.add("b-123-123-123", "test", "12");
        File file = new File("./src/test/data/TempJson.json");
        try {
            testList.exportList(file);
        } catch (Exception e) {
            fail();
        }
        String input = "";
        try (BufferedReader htmlFile = new BufferedReader(new FileReader(file))) {
            // reads file and stores as a string
            String dataRow = htmlFile.readLine();
            while (dataRow != null) {
                input = input.concat(dataRow);
                input = input.concat("\n");
                dataRow = htmlFile.readLine();
            }
        } catch (Exception e) {
            fail();
        }
        assertEquals("""
                {
                  "Inventory": [
                    {
                      "serialNumber": "A-123-123-123",
                      "itemName": "test",
                      "itemValue": "$12.00"
                    },
                    {
                      "serialNumber": "B-123-123-123",
                      "itemName": "test",
                      "itemValue": "$12.00"
                    }
                  ]
                }
                                """, input);

        file.delete();
    }

    @Test
    void importFileTSV() {
        InventoryList testList = new InventoryList();
        File file = new File("./src/test/data/TestTSV.txt");
        // parse
        try {
            testList.importFile(file);
        } catch (Exception e) {
            fail();
        }
        // test
        boolean flag = testList.getDataList().size() == 2;
        if (!testList.getDataList().get(0).compare(new InventoryItem("A-123-123-123", "one", "$12.67")))
            flag = false;
        if (!testList.getDataList().get(1).compare(new InventoryItem("B-123-123-123", "two", "$35.12")))
            flag = false;

        assertTrue(flag);

    }

    @Test
    void importFileHTML() {
        InventoryList testList = new InventoryList();
        // import
        File file = new File("./src/test/data/TestHTML.html");

        //parse
        try {
            testList.importFile(file);
        } catch (Exception e) {
            fail();
        }
        // test
        boolean flag = testList.getDataList().size() == 2;
        if (!testList.getDataList().get(0).compare(new InventoryItem("A-123-12S-21F", "one", "$8091.10")))
            flag = false;
        if (!testList.getDataList().get(1).compare(new InventoryItem("B-1DE-3FW-FE3", "two", "$123.10")))
            flag = false;

        assertTrue(flag);

    }

    @Test
    void importFileJson() {
        InventoryList testList = new InventoryList();
        // import
        File file = new File("./src/test/data/TestJson.json");
        // parse
        try {
            testList.importFile(file);
        } catch (Exception e) {
            fail();
        }
        // test
        boolean flag = testList.getDataList().size() == 2;
        if (!testList.getDataList().get(0).compare(new InventoryItem("X-123-12S-21F", "one", "$123.12")))
            flag = false;
        if (!testList.getDataList().get(1).compare(new InventoryItem("Y-1DE-3FW-FE3", "two", "$3423.10")))
            flag = false;

        assertTrue(flag);


    }
}
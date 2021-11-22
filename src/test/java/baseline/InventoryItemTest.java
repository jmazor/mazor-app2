/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Justin Mazor
 */
package baseline;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void setSerialNumberSuccess() {
        InventoryItem test = new InventoryItem();
        // ensures serial is inputted properly
        test.setSerialNumber("A-1sd-3fd-123");
        assertEquals("A-1sd-3fd-123".toUpperCase(Locale.ROOT), test.getSerialNumber());
    }

    @Test
    void setSerialNumberFailure() {
        InventoryItem test = new InventoryItem();
        // try and set an invalid serial
        try {
            test.setSerialNumber("A1sd-3fd-123");
            // if succeeds fail the test
            fail();
        }
        catch (Exception e) {
            // if we catch an exception it worked as intended
            assertTrue(true);
        }
    }

    @Test
    void setItemNameSuccess() {
        InventoryItem test = new InventoryItem();
        // ensures name is inputted properly
        test.setItemName("test");
        assertEquals("test", test.getItemName());
    }

    @Test
    void setItemNameFailure() {
        InventoryItem test = new InventoryItem();
        // ensures name must be > than 2 chars
        try {
            test.setItemName("a");
            // if succeeds fail the test
            fail();
        }
        catch (Exception e) {
            // if we catch an exception it worked as intended
            assertTrue(true);
        }
    }

    @Test
    void setItemValueSuccess() {
        InventoryItem test = new InventoryItem();
        // ensures value is inputted properly
        test.setItemValue("$13");
        assertEquals("$13.00", test.getItemValue());
    }

    @Test
    void setItemValueFailure() {
        InventoryItem test = new InventoryItem();
        // ensures name is inputted properly
        try {
            test.setItemValue("$-13");
            // if succeeds fail the test
            fail();
        }
        catch (Exception e) {
            // if we catch an exception it worked as intended
            assertTrue(true);
        }
    }

    @Test
    void compare() {
        InventoryItem first = new InventoryItem("a-123-123-123", "test", "12");
        InventoryItem second = new InventoryItem("a-123-123-123", "test", "12");
        assertTrue(first.compare(second));

    }

    @Test
    void getSerialNumber() {
        InventoryItem testItem = new InventoryItem("a-123-123-C12", "test", "12");
        assertEquals("A-123-123-C12", testItem.getSerialNumber());
    }

    @Test
    void getItemName() {
        InventoryItem testItem = new InventoryItem("a-123-123-C12", "test", "12");
        assertEquals("test", testItem.getItemName());

    }

    @Test
    void getItemValue() {
        InventoryItem testItem = new InventoryItem("a-123-123-C12", "test", "12.1");
        assertEquals("$12.10", testItem.getItemValue());

    }

    @Test
    void itemConstructor() {
        InventoryItem test = new InventoryItem("a-123-123-123", "test", "12");

        // tests constructor
        boolean flag = test.getSerialNumber().equals("A-123-123-123".toUpperCase(Locale.ROOT));
        if (!test.getItemName().equals("test"))
            flag = false;
        if (!test.getItemValue().equals("$12.00"))
            flag = false;

        assertTrue(flag);

    }

}
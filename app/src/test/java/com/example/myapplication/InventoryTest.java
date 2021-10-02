package com.example.myapplication;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InventoryTest {

    Inventory inv; // Make sure it is instantiated

    @Test
    public void addInventory()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(it, Inventory.inv.get(0));
        Inventory.addInventory(it);
        assertEquals(100.0f, Inventory.inv.get(0).getQuantity(), 0.0f);
    }


    @Test
    public void size()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(1, Inventory.size());
    }

    @Test
    public void getQuantity()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(50.0f, Inventory.getQuantity("onion"), 0.0f);
    }

    @Test
    public void itemExists()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(0, Inventory.itemExists("onion")); // Should return the index of the result; 0.

        assertEquals(-1, Inventory.itemExists("beef")); // Should return -1; as beef does not exist.
    }

    @Test
    public void findItemQuantity()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Item nit = new Item("beef", 250.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(50.0f, Inventory.findItemQuantity(it), 0.0f); // Should return how much onion we have.
        assertEquals(0.0f, Inventory.findItemQuantity(nit), 0.0f); // Should return 0, since we do not have any beef.
    }

    @Test
    public void getLikeItem()
    {
        Inventory.inv = new ArrayList<Item>();
        Item it = new Item("onion", 50.0f, "grams");
        Item nit = new Item("beef", 250.0f, "grams");
        Inventory.addInventory(it);
        assertEquals(it, Inventory.getLikeItem(it)); // Should return the onion we have.
        assertEquals("notFound", Inventory.getLikeItem(nit).getName()); // Should return notFound item, since we do not have any beef.
    }
}
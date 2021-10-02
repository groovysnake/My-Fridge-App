package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    Item it = new Item("onion", 50.0f, "grams");
    @Test
    public void testaddQuantity()
    {
        it.addQuantity(50.0f);
        assertEquals(100.0f, it.getQuantity(), 0.0f);
        it.addQuantity(-100.0f);
        assertEquals(0f, it.getQuantity(), 0.0f);
    }

    @Test
    public void testsetQuantity()
    {
        it.setQuantity(100.0f);
        assertEquals(100.0f, it.getQuantity(), 0.0f);
    }

    @Test
    public void testgetName()
    {
        assertEquals("onion", it.getName());
    }

    @Test
    public void testgetQuantity()
    {
        assertEquals(50.0f, it.getQuantity(), 0.0f);
    }

    @Test
    public void testgetMeasure_descriptor()
    {
        assertEquals("grams", it.getMeasure_descriptor() );
    }

    @Test
    public void testsetMeasure_descriptor()
    {
        it.setMeasure_descriptor("mls");
        assertEquals("mls", it.getMeasure_descriptor());

    }

    @Test
    public void testsetName()
    {
        it.setName("apple");
        assertEquals("apple", it.getName());

        // Test error case
        it.setName("");
        assertEquals("apple", it.getName());

        it.setName(null);
        assertEquals("apple", it.getName());
    }

}
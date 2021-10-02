package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeTest {

    Item testitems[] = new Item[16];
    Item it = new Item("onion", 50.0f, "grams");
    Recipe testr = new Recipe("Jerk Chicken", "bake", testitems);
    @Test
    public void testHaveAllIngredients()
    {
        Inventory v;
        Inventory.addInventory(it);
        testr.addIngredient(it);

        assertEquals(true, testr.haveAllIngredients());

        testr.addIngredient(new Item("beef", 250.0f, "grams"));
        assertEquals(false, testr.haveAllIngredients());

    }

    @Test
    public void testAddIngredient()
    {
        testr.addIngredient(it);
        assertEquals(it, testr.getIngredients()[0]);
    }

    @Test
    public void testgetDescription()
    {
        assertEquals("bake", testr.getDescription());
    }

    @Test
    public void testgetName()
    {
        assertEquals("Jerk Chicken", testr.getName());
    }

    @Test
    public void testsetName()
    {
        testr.setName("Hamburger");
        assertEquals("Hamburger", testr.getName());
    }

    @Test
    public void testsetDescription()
    {
        testr.setDescription("grill to perfection");
        assertEquals("grill to perfection", testr.getDescription());
    }

    @Test
    public void testgetIngredients()
    {
        Item testitems[] = new Item[16];
        testitems[0] = it;
        testr.addIngredient(it);
        assertEquals(testitems, testr.getIngredients());
    }

    @Test
    public void testgetNumIngredients()
    {
        testr.addIngredient(it);
        assertEquals(1, testr.getNumIngredients());
    }
}
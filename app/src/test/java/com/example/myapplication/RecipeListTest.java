package com.example.myapplication;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RecipeListTest
{
    RecipeList l;
    @Test
    public void size()
    {
        RecipeList.recipes = new ArrayList<Recipe>(); // Singleton testing safe test
        assertEquals(0, RecipeList.size());
        RecipeList.addRecipe(new Recipe("", ""));
        assertEquals(1, RecipeList.size());
    }

    @Test
    public void addRecipe()
    {
        Recipe r = new Recipe("Jerk", "Bake");

        RecipeList.addRecipe(r);
        assertEquals(1, RecipeList.size());
        assertEquals(r, RecipeList.recipes.get(0));
    }
}
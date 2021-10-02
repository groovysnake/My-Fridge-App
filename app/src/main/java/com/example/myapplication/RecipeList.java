package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RecipeList
{
    public static ArrayList<Recipe> recipes = new ArrayList<>();
    public static String fileName = "storage";

    public static int size()
    {
        return recipes.size();
    }

    public static void addRecipe(Recipe r)
    {
        boolean temp = false; // recipe name does not already exist
        int t = 0;
        for (int i = 0; i < recipes.size(); i++)
        {
            if(r.getName().equalsIgnoreCase(recipes.get(i).getName()))
            {
                temp = true;
                t++;
            }
        }

        if(temp) // Must change name
        {
            r.setName(r.getName() + " " + t);
            recipes.add(r);
        }
        else // recipe name does not already exist
        {
            recipes.add(r);
        }
    }

    public static void save(Context c)
    {
        try
        {
            FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            for (int i = 0; i < size(); i++)
            {
                os.writeObject(recipes.get(i));
                Log.i("RecipeList.class", "saved a recipe: " + recipes.get(i).getName());
            }
            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            Log.i("RecipeList.class", e.getMessage() + "");
        }
    }
}

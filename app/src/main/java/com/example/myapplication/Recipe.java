package com.example.myapplication;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Recipe implements Parcelable, Serializable
{
    private String name;
    private String description;
    //private ArrayList<Item> ingredients;
    private Item[] ingredients = new Item[16];


    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(description);
        out.writeTypedArray(ingredients, 0);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>()
    {
        public Recipe createFromParcel(Parcel in)
        {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size)
        {
            return new Recipe[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Recipe(Parcel in) {
        name = in.readString();
        description = in.readString();
        ingredients = in.createTypedArray(Item.CREATOR);
    }

    public Recipe(String n, String s, Item[] al)
    {
        name = n;
        description = s;
        ingredients = al;
    }

    public Recipe(String n, String s)
    {
        name = n;
        description = s;
    }

    public boolean haveAllIngredients()
    {
        Inventory v; // Make sure inventory is instantiated

        for (int i = 0; i < ingredients.length; i++)
        {
            //Log.i("haveAllIngredients", "going through ingredients of " + name + i);
            if (ingredients[i] != null)
            {
                //Log.i("haveAllIngredients", "found an ingredient");
                Item item = ingredients[i]; // Item in recipe
                Item stockItem = Inventory.getLikeItem(item); // How much we have in inventory

                if (stockItem.getName().equals("notFound"))
                    return false; // We have no "item"

                if (item.getMeasure_descriptor().equals(stockItem.getMeasure_descriptor()))
                {
                    // Measured using the same measurement
                    if (item.getQuantity() > stockItem.getQuantity())
                    {
                        return false;
                    }
                }

                // Handle different measurement descriptors

            }
        }

        return true;
    }

    public boolean addIngredient(Item it) // returns true on success, false on fail
    {
        for (int i = 0; i < ingredients.length; ++i)
        {
            if (ingredients[i] == null)
            {
                ingredients[i] = it;
                return true;
            }
        }
        return false;
    }

    public String getDescription()
    {
        return description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void setDescription(String s)
    {
        description = s;
    }

    public Item[] getIngredients()
    {
        return ingredients;
    }

    public int getNumIngredients()
    {
        int i = 0;
        while(ingredients[i] != null)
        {
            i++;
        }
        return i;
    }



}

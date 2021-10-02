package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Inventory
{
    //static ArrayList<Food> inv = new ArrayList<>();
    //static Map<String, Integer> inv = new HashMap<>();
    public static ArrayList<Item> inv = new ArrayList<>();
    public static String fileName = "storageItems";


    public static void addInventory(String a_itemName, int q)
    {
        //inv.put(item, inv.getOrDefault(item, 0) + q);
        int indexOfFoundItem = itemExists(a_itemName);

        if (indexOfFoundItem != -1)
        {
            inv.get(indexOfFoundItem).addQuantity(q);
        }
        else
        {
            inv.add(new Item(a_itemName, q));
        }
    }

    public static void addInventory(Item im)
    {
        int indexOfFoundItem = itemExists(im.getName());

        if (indexOfFoundItem != -1)
        {
            inv.get(indexOfFoundItem).addQuantity(im.getQuantity());
            if (inv.get(indexOfFoundItem).getQuantity() <= 0)
            {
                inv.remove(indexOfFoundItem);
            }
        }
        else
        {
            inv.add(im);
        }


    }

    public static int size()
    {
        return inv.size();
    }

    public static float getQuantity(String item)
    {
        int ind = itemExists(item);
        if (ind != -1)
            return inv.get(ind).getQuantity();
        else
            return -1;
    }

    static int itemExists(String n)
    {
        for (int i = 0; i < inv.size(); i++)
        {
            if (inv.get(i).getName().equalsIgnoreCase(n))
            {
                return i;
            }
        }

        return -1;
    }

    public static float findItemQuantity(Item it)
    {
        for (int i = 0; i < inv.size(); i++)
        {
            if (it.getName().equals(inv.get(i).getName()))
            {
                return inv.get(i).getQuantity();
            }
        }

        return 0;
    }

    public static Item getLikeItem(Item it)
    {
        for (int i = 0; i < inv.size(); i++)
        {
            if (it.getName().equalsIgnoreCase(inv.get(i).getName()))
            {
                Item temp = inv.get(i);
                return temp;
            }
        }

        Item notFound = new Item("notFound", 0, "");
        return notFound;
    }

    public static void save(Context c)
    {
        try
        {
            FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            for (int i = 0; i < size(); i++)
            {
                os.writeObject(inv.get(i));
                Log.i("Inventory.class", "Saved an item: " + inv.get(i).getName());
            }

            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            Log.i("Inventory.Java", e.getMessage() + "");
        }
    }

}

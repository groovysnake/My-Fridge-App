package com.example.myapplication;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item implements Parcelable, Serializable
{
    private String name;
    private String measure_descriptor;
    private float quantity;
    public String fileName = "storageItems";

    Item(String n, float q)
    {
        name = n;
        quantity = q;
        measure_descriptor = "cups";
    }

    Item(String n, float q, String m)
    {
        name = n;
        quantity = q;
        measure_descriptor = m;
    }


    public void addQuantity(float q)
    {
        quantity += q;
    }

    public void setQuantity(float q)
    {
        quantity = q;
    }

    public String getName()
    {
        return name;
    }

    public float getQuantity()
    {
        return quantity;
    }

    public String getMeasure_descriptor()
    {
        return measure_descriptor;
    }

    public void setMeasure_descriptor(String s)
    {
        measure_descriptor = s;
    }



    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents()
    {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(name);
        out.writeString(measure_descriptor);
        out.writeFloat(quantity);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Item(Parcel in)
    {
        name = in.readString();
        measure_descriptor = in.readString();
        quantity = in.readFloat();
    }

    public void setName(String name)
    {
        if (name != null) {
            if (!name.equals("")) {
                this.name = name;
            } else {
                // Log error
            }
        }
        else
        {
            // Log null string error
        }
    }

    public void save(Context c)
    {
        try
        {
            FileOutputStream fos = c.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            Log.i("Recipe.Java", e.getMessage());
        }
    }
}

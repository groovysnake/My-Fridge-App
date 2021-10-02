package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MainActivity";

    String fileName = "storage";
    String fileNameItems = "storageItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        loadRecipes();

        loadFridgeItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch(id)
        {
            case R.id.action_two:
                Log.i(ACTIVITY_NAME, "in the information action");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(/*R.string.dialog_OK*/"Went shopping? Add items to your Fridge!\n\n" +
                        "Want to make something? Check your Recipes!\n\n" +
                        "Have a recipe you'd like to save? Add a new Recipe!\n\n" +
                        "Missing something? Check the weather before heading to the grocery store!")
                        .setPositiveButton(/*R.string.fire*/"Thanks!", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // What to do on Accept goes here
                            }
                        });
                builder.create().show();
                        //.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        //    public void onClick(DialogInterface dialog, int id) {
                        //        // What to do on Cancel goes here
                        //    }
                        //});
                break;
            case R.id.about:
                Log.i(ACTIVITY_NAME, "in the about action");
                Toast.makeText(this, R.string.about_desc,
                        Toast.LENGTH_SHORT).show();
                break;

        }
        return true ;
    }

    public void onFridgeClicked(android.view.View view)
    {
        Intent intent = new Intent(MainActivity.this,
                FridgeInventoryActivity.class);
        startActivity(intent);
    }

    public void onRecipesClicked(android.view.View view)
    {
        Intent intent = new Intent(MainActivity.this,
                RecipeViewActivity.class);
        startActivity(intent);
    }

    public void onAddRecipeClicked(android.view.View view)
    {
        Intent intent = new Intent(MainActivity.this,
                AddRecipeActivity.class);
        startActivity(intent);
    }

    public void onWeatherClicked(android.view.View view)
    {
        Intent intent = new Intent(MainActivity.this,
                WeatherForecastActivity.class);
        startActivity(intent);
    }

    public void loadRecipes()
    {
        ArrayList<Recipe> objectsList = new ArrayList<Recipe>();
        boolean cont = true;

        try {
            FileInputStream fis = this.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Log.i(ACTIVITY_NAME, "Starting to look for recipes in the book...");
            while (cont)
            {
                Recipe r = null;
                try
                {
                    Log.i(ACTIVITY_NAME, "Reading recipe...");
                    r = (Recipe) is.readObject();
                }
                catch (Exception e)
                {
                    cont = false;
                    Log.i(ACTIVITY_NAME, e.getMessage() + "; Recipe is null.");
                }

                if (r != null)
                {
                    objectsList.add(r);
                    Log.i(ACTIVITY_NAME, "Recipe found! " + r.getName());
                }
                else
                    {
                Log.i(ACTIVITY_NAME, "End of recipes. :(");
                cont = false;
                }
            }
            is.close();
            fis.close();
        }
        catch (Exception e)
        {
            Log.i(ACTIVITY_NAME, e.getMessage() + "");
        }

        for (int i = 0; i < objectsList.size(); i++)
        {
            RecipeList.addRecipe(objectsList.get(i));
        }
    }
    public void loadFridgeItems()
    {
        ArrayList<Item> objectsList = new ArrayList<Item>();
        boolean cont = true;

        try {
            FileInputStream fis = this.openFileInput(fileNameItems);
            ObjectInputStream is = new ObjectInputStream(fis);
            Log.i(ACTIVITY_NAME, "Starting to look for food in the fridge...");
            while (cont)
            {
                Item it = (Item) is.readObject();
                if (it != null)
                {
                    objectsList.add(it);
                    Log.i(ACTIVITY_NAME, "Food item found! " + it.getName());
                }
                else
                {
                    Log.i(ACTIVITY_NAME, "End of items. :(");
                    cont = false;
                }
            }
            is.close();
            fis.close();
        }
        catch (Exception e)
        {
            Log.i(ACTIVITY_NAME, e.getMessage() + "");
        }

        for (int i = 0; i < objectsList.size(); i++)
        {
            Inventory.addInventory(objectsList.get(i));
        }
    }
}

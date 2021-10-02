package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class AddRecipeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    protected static final String ACTIVITY_NAME = "AddRecipeActivity";


    EditText itemNameEdit, qtyEdit, descEdit, nameEdit;
    Button addItemButton, addRecipeButton;
    Spinner spinner;
    TextView addIngredientsText;

    String measure;
    String fileName = "storage";

    Recipe r;
    RecipeList rl = new RecipeList();


    int ing = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        itemNameEdit = findViewById(R.id.item_name_textedit);
        nameEdit = findViewById(R.id.name_textedit);
        qtyEdit = findViewById(R.id.quantity_text_edit);
        addItemButton = findViewById(R.id.send_button);
        descEdit = findViewById(R.id.desc_edit);
        addRecipeButton = findViewById(R.id.add_recipe_button);
        addIngredientsText = findViewById(R.id.textView);

        r = new Recipe("", "");

        //Spinner
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurements_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addRecipeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String tempName = nameEdit.getText().toString();
                String tempDesc = descEdit.getText().toString();

                if (r.getNumIngredients() > 0)
                {
                    if (!tempName.equals(""))
                    {
                        r.setName(tempName);
                        if (!tempDesc.equals(""))
                        {
                            r.setDescription(tempDesc);
                            RecipeList.addRecipe(r);
                            Toast.makeText(getApplicationContext(),"Recipe Added: " + r.getName(),Toast.LENGTH_SHORT).show();
                            //Log.i(ACTIVITY_NAME, "New Recipe Added: " + r.getName());
                            nameEdit.setText("");
                            descEdit.setText("");
                            addIngredientsText.setText("Add Ingredients:");
                            Log.i(ACTIVITY_NAME, "Num ingredients: " + r.getNumIngredients());

                            // Reset recipe
                            r = new Recipe("", "");
                        }
                        else
                        {
                            Snackbar mySnackbar = Snackbar.make(nameEdit, "Please enter instructions!", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                            return;
                        }
                    }
                    else
                    {
                        Snackbar mySnackbar = Snackbar.make(nameEdit, "Please enter a name for this recipe!", Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                        return;
                    }
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(nameEdit, "Please add at least one ingredient!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    return;
                }
            }
        });
        addItemButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String temp_item_name = itemNameEdit.getText().toString();
                String tempQty = qtyEdit.getText().toString();
                Log.i(ACTIVITY_NAME, "tempQty:" + tempQty + "'");
                float temp_item_qty = -1.0f;

                if (!tempQty.equals(""))
                {
                    Log.i(ACTIVITY_NAME, "Got in somehow!!");
                    temp_item_qty = Float.parseFloat(tempQty);
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(nameEdit, "Please enter a quantity for the item!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    return;
                }


                if (!temp_item_name.equals(""))
                {
                    if (temp_item_qty > 0)
                    {
                        Item tempItem = new Item(temp_item_name,temp_item_qty);
                        tempItem.setMeasure_descriptor(measure);

                        //Adding the ingredient to the recipe as it adds
                        r.addIngredient(tempItem);

                        addIngredientsText.setText(addIngredientsText.getText()
                                + "\n"
                                + ing + ". " + tempItem.getName() + ": " + tempItem.getQuantity() + measure);

                        ing++;
                        itemNameEdit.setText("");
                        qtyEdit.setText("");
                        return;
                    }
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(nameEdit, "Please enter a name for the item!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    return;
                }
            }
        });
    } // onCreate

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        measure = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onDestroy()
    {
        super.onDestroy();
  //     File internalStorageDir = getFilesDir();
  //     File storage = new File(internalStorageDir, "storage.csv");
  //     // Create file output stream

  //     // Write a line to the file
  //     try {
  //         FileOutputStream fos = new FileOutputStream(storage);
  //         for (int i = 0; i < RecipeList.size(); i++)
  //         {
  //             fos.write(RecipeList.recipes.get(i).getBytes());
  //         }
  //     }
  //     catch(Exception r)
  //     {
  //         Log.i(ACTIVITY_NAME, r.getMessage());
  //     }
  //     // Close the file output stream
  //     fos.close();

        // SAVE!!!
        //for (int i = 0; i < RecipeList.recipes.size(); i++)
        //{
        //    RecipeList.recipes.get(i).save(this);
        //    Log.i(ACTIVITY_NAME, "Saved a recipe! " + RecipeList.recipes.get(i).getName());
        //}
        RecipeList.save(this);


    }


}

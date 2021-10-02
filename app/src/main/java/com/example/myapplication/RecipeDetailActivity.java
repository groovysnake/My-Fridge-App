package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeDetailActivity extends AppCompatActivity
{
    Recipe rec;
    TextView nameText;
    TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        nameText = findViewById(R.id.textView_name);
        descText = findViewById(R.id.textView_description);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        rec = extras.getParcelable("therecipe");


        if(rec != null)
        {
            nameText.setText(rec.getName());

            String ing = "";
            ing += "Ingredients: \n";
            for (int i = 0; i < rec.getIngredients().length; i++)
            {
                if (rec.getIngredients()[i] != null)
                {
                    Item tempIng = rec.getIngredients()[i];
                    ing += "" + (i + 1) + ". " +
                            tempIng.getName() + ": " +
                            tempIng.getQuantity() + " " +
                            tempIng.getMeasure_descriptor() + "\n";
                }
            }

            descText.setText((ing + "\n" + rec.getDescription()));
        }
        else
        {
            nameText.setText("Error");
            descText.setText("Error\nError");
        }
    }
}

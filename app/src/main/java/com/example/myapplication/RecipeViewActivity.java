package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class RecipeViewActivity extends AppCompatActivity {



    protected static final String ACTIVITY_NAME = "RecipeViewActivity";

    Button sendButton;
    EditText messageEditText;
    EditText quantityEditText;
    ListView listView;
    //ArrayList<String> messages = new ArrayList<>();
    //Inventory fridge = new Inventory();
    RecipeList rl = new RecipeList();
    //ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        //sendButton = findViewById(R.id.send_button);
        //messageEditText = findViewById(R.id.message_text_edit);
        //quantityEditText = findViewById(R.id.quantity_text_edit);
        listView = findViewById(R.id.listview1);
        final ChatAdapter messageAdapter = new ChatAdapter( this );

        Recipe r = new Recipe("Grilled Cheese", "Instructions:\n1. Add cheese to bread\n2. Grill sandwich"); // A test
        Log.i("Creating a recipe", valueOf(r.addIngredient(new Item("Cheese", 250, "grams"))));
        //RecipeList.addRecipe(r);

        //in this case, “this” is the ChatWindow, which is-A Context object
        listView.setAdapter (messageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(),RecipeDetailActivity.class);
                intent.putExtra("therecipe", (Parcelable)RecipeList.recipes.get(i));
                startActivity(intent);
            }
        });

    }



    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return RecipeList.size();
        }

        public Recipe getRecipe(int position)
        {
            return RecipeList.recipes.get(position);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = RecipeViewActivity.this.getLayoutInflater();
            View result;

            Log.i(ACTIVITY_NAME, Integer.toString(position));
            if(position%2 == 0)
                result = inflater.inflate(R.layout.recipe_item, null);
            else
                result = inflater.inflate(R.layout.recipe_item, null);

            TextView recipename = result.findViewById(R.id.item_name);
            ImageView image = result.findViewById(R.id.availability_image);
            Recipe theRecipe = getRecipe(position);
            recipename.setText(theRecipe.getName() ); // get the string at position

            if (theRecipe.haveAllIngredients())
            {
                Log.i("RecipeView", "set image to available");
                image.setImageDrawable(getDrawable(android.R.drawable.presence_online));
            }
            else
            {
                image.setImageDrawable((getDrawable(android.R.drawable.presence_offline)));
            }

            //TextView quantityMessage = result.findViewById(R.id.item_quantity);
            //quantityMessage.setText(String.valueOf(getInvItem(position).getQuantity()));
            return result;
        }

    }
}

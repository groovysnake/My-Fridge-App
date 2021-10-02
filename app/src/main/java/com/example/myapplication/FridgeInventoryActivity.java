package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FridgeInventoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{


    protected static final String ACTIVITY_NAME = "FridgeInventoryActivity";

    Button sendButton;
    EditText messageEditText;
    EditText quantityEditText;
    ListView listView;
    Spinner spinner;
    //ArrayList<String> messages = new ArrayList<>();
    Inventory fridge = new Inventory();
    //ChatAdapter messageAdapter;

    String measure;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_inventory);
        sendButton = findViewById(R.id.send_button);
        messageEditText = findViewById(R.id.message_text_edit);
        quantityEditText = findViewById(R.id.quantity_text_edit);
        listView = findViewById(R.id.listview1);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurements_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final ChatAdapter messageAdapter = new ChatAdapter( this );

        //in this case, “this” is the ChatWindow, which is-A Context object
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Code here executes on main thread after user presses button
                String temp = messageEditText.getText().toString(); // Item name
                String tempQty = quantityEditText.getText().toString();
                float temp_item_qty = -1.0f;

                if (!tempQty.equals(""))
                {
                    temp_item_qty = Float.parseFloat(tempQty);
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(messageEditText, "Please enter a quantity for the item!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    return;
                }

                if (!temp.equals(""))
                {
                    Item tempI = new Item(temp, temp_item_qty, measure);
                    //messages.add(temp);
                    //Inventory.addInventory(temp, 5);
                    //Inventory.addInventory(tempI.getName(), tempI.getQuantity());
                    Inventory.addInventory(tempI);
                    messageEditText.getText().clear();
                    quantityEditText.getText().clear();
                    //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //startActivity(intent);
                    messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
                }
                else
                {
                    Snackbar mySnackbar = Snackbar.make(messageEditText, "Please enter a name for this item!", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    return;
                }
            }
        });


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        measure = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            //return messages.size(); // Return the number of items in array of messages
            return Inventory.size();
        }

        //public String getItem(int position)
        {
            //return messages.get(position);
        }
        public Item getInvItem(int position)
        {
            return Inventory.inv.get(position);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = FridgeInventoryActivity.this.getLayoutInflater();
            View result;

            Log.i(ACTIVITY_NAME, Integer.toString(position));
            if(position%2 == 0)
                result = inflater.inflate(R.layout.inv_item, null);
            else
                result = inflater.inflate(R.layout.inv_item, null);

            TextView message = result.findViewById(R.id.item_name);
            message.setText(   /*"Hello"*/ getInvItem(position).getName() ); // get the string at position

            TextView quantityMessage = result.findViewById(R.id.item_quantity);
            quantityMessage.setText((String.valueOf(getInvItem(position).getQuantity()) + " " +
                    getInvItem(position).getMeasure_descriptor()));
            return result;
        }

    }

    public void onDestroy()
    {
        super.onDestroy();

        //for (int i = 0; i < Inventory.size(); i++)
        //{
        //    Log.i(ACTIVITY_NAME, ((Boolean)(i < Inventory.size())).toString());
        //    Inventory.inv.get(i).save(this);
        //    Log.i(ACTIVITY_NAME, "Saved an item!");
        //}
        Inventory.save(this);
    }

}

package siu.example.com.to_dolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN";
    public static final int REQUEST_CODE = 42;
    public static final int ITEM_REQUEST_CODE = 33;
    public static final String DATA_KEY = "myDataKey";
    public static final String INTENT_KEY = "result";
    public static final String BUNDLE_KEY = "returnList";

    ListView myToDoListsView;
    FloatingActionButton addNewListButton;
    ArrayAdapter<String> myListsAdapter;
    ArrayList<String> myListsData;
    Intent toItemActivity;
    ArrayList<String> returnedData;
    HashMap<String, ArrayList<String>> myHashMapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Activity Initialization
        setViews();

        // Create Intent
        toItemActivity = new Intent(MainActivity.this, ListItemsActivity.class);

        // Data: myListsData for Adapted, returnedData for HashMap
        myListsData = new ArrayList<>();
        returnedData = new ArrayList<>();
        myHashMapData = new HashMap<>();
        Log.d(TAG, "ON CREATE PRINT HASHMAP: " + myHashMapData);

        //Set Adapter
        myListsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, myListsData);
        myToDoListsView.setAdapter(myListsAdapter);

        //Click FAB to create a new list
        addNewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove data from intent when clicking new list button
                toItemActivity.removeExtra(DATA_KEY);
                startActivityForResult(toItemActivity, REQUEST_CODE);
            }
        });

        //Click item to edit list
        myToDoListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Take clicked item as key for hashmap and return corresponding data
                String clickedItem = ((TextView) view).getText().toString();
                ArrayList<String> savedItemData = myHashMapData.get(clickedItem);
                Log.d("Main", "++++++++WHEN ITEM CLICKED PRINT HASHMAP" + savedItemData);

                toItemActivity.putStringArrayListExtra(DATA_KEY, savedItemData);
                startActivityForResult(toItemActivity, ITEM_REQUEST_CODE);
            }
        });

        // StrikeThrough on long item click
        myToDoListsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentItem = (TextView) view;
                strikeThroughTextView(currentItem);
                return true;
            }
        });

    }

    private void setViews(){
        myToDoListsView = (ListView)findViewById(R.id.main_toDoListName_listView);
        addNewListButton = (FloatingActionButton)findViewById(R.id.main_addNewList_button);

    }

    // Returns data from other activity and stores to adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle data when new list is clicked
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle returnedBundle = data.getBundleExtra(INTENT_KEY);
                handleReturnedData(returnedBundle);
            }
        }
        // Handle data returned from item click
        if(requestCode == ITEM_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Bundle returnedBundle = data.getBundleExtra(INTENT_KEY);
                handleReturnedData(returnedBundle);
            }
        }
    }

    private void handleReturnedData(Bundle returnedBundle){
        returnedData = returnedBundle.getStringArrayList(BUNDLE_KEY);
        String listTitle = returnedData.get(returnedData.size()-1);

        //Check hashmap for key before displaying in main
        if(myHashMapData.containsKey(listTitle)){
            Log.d(TAG, "LIST TITLE ALREADY INSIDE THE HASHMAP");
            myHashMapData.put(listTitle, returnedData);
        }else{
            myHashMapData.put(listTitle, returnedData);
            Log.d(TAG, "=========LIST TITLE NOT IN HASHMAP" + myHashMapData);
            myListsAdapter.add(listTitle);
            myListsAdapter.notifyDataSetChanged();
        }
    }

    public void strikeThroughTextView(TextView currentItem){
        int paintFlags = currentItem.getPaintFlags();
        int noStrikeThroughFlag = 1281;
        if(paintFlags == noStrikeThroughFlag){
            currentItem.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
        } else{
            currentItem.setPaintFlags(noStrikeThroughFlag);
        }
    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}




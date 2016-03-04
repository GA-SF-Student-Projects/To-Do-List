package siu.example.com.to_dolist;

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

        initializeActivity();
        longItemClickStrikeThrough();


        Log.d(TAG,"ON CREATE PRINT HASHMAP: " + myHashMapData);

        /// Setting the Adapter with new data
        returnedData = new ArrayList<>();
        myToDoListsView.setAdapter(myListsAdapter);

        //Goes to new activity to create a new list
        addNewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toItemActivity.removeExtra(DATA_KEY);
                startActivityForResult(toItemActivity, REQUEST_CODE);
            }
        });

        myToDoListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get item from view, then through into hashmap, then push to an array list
                String clickedItem = ((TextView)view).getText().toString();
                Log.d("Main", "++++++++WHEN CLICKED PRINT HASHMAP"+myHashMapData.get(clickedItem));

                toItemActivity.putStringArrayListExtra(DATA_KEY, myHashMapData.get(clickedItem));
                startActivityForResult(toItemActivity, ITEM_REQUEST_CODE);
            }
        });
    }

    private void initializeActivity(){
        myToDoListsView = (ListView)findViewById(R.id.main_toDoListName_listView);
        addNewListButton = (FloatingActionButton)findViewById(R.id.main_addNewList_button);
        myListsData = new ArrayList<>();
        myListsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, myListsData);
        toItemActivity = new Intent(MainActivity.this, ListItemsActivity.class);
        myHashMapData = new HashMap<>();
    }

    // Returns data from other activity and stores to adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Bundle returnedBundle = data.getBundleExtra("result");
                returnedData = returnedBundle.getStringArrayList("returnList");
                String listTitle = returnedData.get(returnedData.size()-1);
                if(myHashMapData.containsKey(listTitle)){
                    Log.d(TAG, "TAG ALREADY INSIDE THE HASHMAP");
                    myHashMapData.put(listTitle, returnedData);
                }else{
                    myHashMapData.put(listTitle, returnedData);
                    Log.d("Main", "===============Returned Data" + myHashMapData);

                    myListsAdapter.add(listTitle);
                    myListsAdapter.notifyDataSetChanged();
                }
            }
        }

        if(requestCode == ITEM_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Bundle returnedBundle = data.getBundleExtra("result");
                returnedData = returnedBundle.getStringArrayList("returnList");
                String listTitle = returnedData.get(returnedData.size()-1);
                myHashMapData.put(listTitle, returnedData);
                Log.d("Main", "===============Returned Data" + myHashMapData);
                myListsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void longItemClickStrikeThrough() {
        myToDoListsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentItem = (TextView) view;
                strikeThroughTextView(currentItem);
                return true;
            }
        });
    }

    private void strikeThroughTextView(TextView currentItem){
        int paintFlags = currentItem.getPaintFlags();
        if(paintFlags == 1281){
            currentItem.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
        } else{
            currentItem.setPaintFlags(1281);
        }
    }
}




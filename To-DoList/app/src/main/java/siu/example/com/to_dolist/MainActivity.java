package siu.example.com.to_dolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 42;
    ListView myToDoListsView;
    FloatingActionButton addNewListButton;
    ArrayAdapter<String> myListsAdapter;
    ArrayList<String> myListsData;
    Intent toItemActivity;
    ArrayList<String> returnedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivity();

        /// Setting the Adapter with new data
        returnedData = new ArrayList<>();
        myToDoListsView.setAdapter(myListsAdapter);

        myToDoListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle sendBundle = new Bundle();
                sendBundle.putStringArrayList("sendList", myListsData);

                // ++++++ ==== DOES NOT WORK ======
                // CHANGE TO SEND HAS MAP DATA
                startActivityForResult(toItemActivity, REQUEST_CODE, sendBundle);
            }
        });

        //Goes to new activity to create a new list
        addNewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(toItemActivity, REQUEST_CODE);
            }
        });

    }

    private void initializeActivity(){
        myToDoListsView = (ListView)findViewById(R.id.main_toDoListName_listView);
        addNewListButton = (FloatingActionButton)findViewById(R.id.main_addNewList_button);
        myListsData = new ArrayList<>();
        myListsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, myListsData);
        toItemActivity = new Intent(MainActivity.this, ListItemsActivity.class);
    }

    // Returns data from other activity and stores to adapter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                Bundle returnedBundle = data.getBundleExtra("result");
                // Store Data to different HASHMAPS, so there won't be duplicates

                myListsAdapter.addAll(returnedBundle.getStringArrayList("returnList").get(0));

                //myListsAdapter.add(data.getStringExtra("result"));
                myListsAdapter.notifyDataSetChanged();
            }
        }
    }

}

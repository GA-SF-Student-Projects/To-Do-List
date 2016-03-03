package siu.example.com.to_dolists;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView myListsView;
    FloatingActionButton newListButton;
    Intent toListActivity;
    Intent ListActivity;
    ArrayList<String> toDoListItems;
    ArrayAdapter<String> returnList;
    TextView testingInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hashtable<String, ArrayList<String>> StoredList = new Hashtable<>();


        setActivityObjects();

        toDoListItems = ListActivity.getStringArrayListExtra("toDoList");
        //testingInput.setText(toDoListItems.get(0));
        if(toDoListItems == null){
            Log.v("MainActivity", "Empty");
        }else{


            returnList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoListItems);
            myListsView.setAdapter(returnList);

            StoredList.put(toDoListItems.get(toDoListItems.size() - 1),toDoListItems);
            Set<String> keys = StoredList.keySet();
            System.out.println("--------StoredLIst-----------"+StoredList.get("Hello"));

        }


        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toListActivity);
            }
        });

    }

    private void setActivityObjects(){
        myListsView = (ListView)findViewById(R.id.main_activity_listview);
        newListButton = (FloatingActionButton)findViewById(R.id.main_activity_fab);

        toDoListItems = new ArrayList<>();
        toListActivity = new Intent(MainActivity.this, ListActivity.class);

        testingInput = (TextView)findViewById(R.id.main_activity_test_edittext);

        ListActivity = getIntent();

    }


}

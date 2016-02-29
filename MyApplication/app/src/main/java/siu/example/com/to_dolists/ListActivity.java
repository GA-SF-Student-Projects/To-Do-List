package siu.example.com.to_dolists;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListActivity extends AppCompatActivity {

    EditText myListName;
    EditText myNewToDoItem;
    FloatingActionButton addListActivity;
    Button returnToMainActivity;
    ArrayList<String> storeToDoItems;
    ArrayAdapter<String> toDoAdapter;
    ListView myToDoItems;
    Intent toMainActivity;
    String todoInput;
    String listNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setActivityObjects();

        addListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoInput = myNewToDoItem.getText().toString();
                storeToDoItems.add(todoInput);
                toDoAdapter.notifyDataSetChanged();
            }
        });

        returnToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listNameInput = myListName.getText().toString();
                storeToDoItems.add(listNameInput);
                toMainActivity.putStringArrayListExtra("toDoList", storeToDoItems);
                startActivity(toMainActivity);
            }
        });
    }

    private void setActivityObjects(){
        myListName = (EditText)findViewById(R.id.list_activity_edittext);
        myNewToDoItem = (EditText)findViewById(R.id.list_activity_edittext_todoItem);
        addListActivity = (FloatingActionButton)findViewById(R.id.list_activity_fab);
        returnToMainActivity = (Button)findViewById(R.id.list_activity_button_toMainActivity);
        myToDoItems = (ListView)findViewById(R.id.list_activity_listview);

        toMainActivity = new Intent(ListActivity.this, MainActivity.class);

        storeToDoItems = new ArrayList<>();
        toDoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storeToDoItems);
        myToDoItems.setAdapter(toDoAdapter);

    }
}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListItemsActivity extends AppCompatActivity {
    public static final String TAG = "MAIN";

    ListView itemsListView;
    FloatingActionButton addItemButton;
    ArrayAdapter<String> newItemAdapter;
    EditText inputListTitle;
    EditText inputListItem;
    ArrayList<String> listItems;
    ArrayList<String> listSentFromMain;
    Intent dataIntent;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        Log.d(TAG, "On Create: Loaded;");

        // Initialize Activity
        setViews();

        checkListItems();

        // Handle data from MainActivity
        dataIntent = getIntent();
        updateListItems(dataIntent);

        newItemAdapter = new ArrayAdapter<>(ListItemsActivity.this, android.R.layout.simple_list_item_1, listItems);
        itemsListView.setAdapter(newItemAdapter);

        // Save data to Main Activity
        saveButtonClickedReturnHome();

        // Add new item to list
        addItemToListButtonClicked();

        // Strike through item on list
        strikeThroughListViewItem();
    }

    private void updateListItems(Intent dataIntent){
        if(dataIntent == null){
            Log.d(TAG, "+++++++++getIntent() FROM MAIN IS NULL "+ dataIntent);
        }else{
            listSentFromMain = dataIntent.getStringArrayListExtra(MainActivity.DATA_KEY);
            if(listSentFromMain != null){
                String titleFromMain = listSentFromMain.get(listSentFromMain.size()-1);
                inputListTitle.setText(titleFromMain);
                removeTitle();
                //listItems = new ArrayList<>(listSentFromMain.size());
                //Collections.copy(listItems, listSentFromMain);
                listItems = listSentFromMain;
                Log.d(TAG, "+++++++++ListSentFromMain: NOTNULL" + listItems + "-----" + listSentFromMain.size());
                Log.d(TAG, "+++++++++ListItems: NOTNULL" + listItems + "-----" + listItems.size());
            }
        }
    }

    private void checkListItems(){
        if (listItems == null){
            listItems = new ArrayList<>();
        }
    }

    private void setViews(){
        itemsListView = (ListView)findViewById(R.id.list_item_listview);
        addItemButton = (FloatingActionButton)findViewById(R.id.list_item_addList_button);
        inputListTitle = (EditText)findViewById(R.id.list_item_title_edittext);
        inputListItem = (EditText)findViewById(R.id.list_item_input_edittext);
        saveButton = (Button)findViewById(R.id.list_temp_button);
    }

    // Add new item to list
    private void addItemToListButtonClicked(){
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addNewItem = inputListItem.getText().toString();

                if (checkListItemInput()) {
                    inputListItem.setError("Pleast input an item");
                    //Toast.makeText(ListItemsActivity.this, "Please input an item", Toast.LENGTH_SHORT).show();
                } else {
                    inputListItem.getText().clear();
                    listItems.add(addNewItem);
                    Log.d(TAG, "----ADD ITEM TO LIST BUTTON IS CLICKED, ITEMS IN LIST: " + listItems);
                    newItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    // Save items to Main Activity
    private void saveButtonClickedReturnHome() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkListTitleInput()) {
                    //Toast.makeText(ListItemsActivity.this, "Please enter a list title", Toast.LENGTH_SHORT).show();
                    inputListTitle.setError("Please enter a list title");
                } else {
                    // Append title to bottom of our list
                    listItems.add(inputListTitle.getText().toString());

                    Log.d(TAG, ">>>>>>>>>>>>BUTTON TO HOME CLICKED: SAVE ITEMS" + listItems);

                    Bundle listBundle = new Bundle();
                    listBundle.putStringArrayList(MainActivity.BUNDLE_KEY, listItems);

                    dataIntent.putExtra(MainActivity.INTENT_KEY, listBundle);
                    setResult(RESULT_OK, dataIntent);
                    finish();   // Go to garbage collecting and remove from memory
                }
            }
        });
    }

    private void strikeThroughListViewItem(){
        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentItem = (TextView) view;
                strikeThroughTextView(currentItem);
                return true;
            }
        });
    }

    // StrikeThrough text
    public void strikeThroughTextView(TextView currentItem){
        int paintFlags = currentItem.getPaintFlags();
        int noStrikeThroughFlag = 1281;
        if(paintFlags == noStrikeThroughFlag){
            currentItem.setPaintFlags(paintFlags | Paint.STRIKE_THRU_TEXT_FLAG);
        } else{
            currentItem.setPaintFlags(noStrikeThroughFlag);
        }
    }

    private boolean checkListTitleInput(){
        return inputListTitle.getText().toString().isEmpty();
    }

    private boolean checkListItemInput(){
        return inputListItem.getText().toString().isEmpty();
    }


    private void removeTitle(){
        listSentFromMain.remove(listSentFromMain.size() - 1);
    }
}




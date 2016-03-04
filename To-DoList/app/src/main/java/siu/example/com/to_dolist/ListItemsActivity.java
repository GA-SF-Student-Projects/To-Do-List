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

public class ListItemsActivity extends AppCompatActivity {

    ListView itemsListView;
    FloatingActionButton addItemButton;
    ArrayAdapter<String> newItemAdapter;
    EditText inputListTitle;
    EditText inputListItem;
    ArrayList<String> listItems;
    ArrayList<String> listSentFromMain;
    Intent data;

    Button tempButton;
    Intent testIntent;
    ArrayList<String> testArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);


        initializeActivity();

        listItems = new ArrayList<>();
        newItemAdapter = new ArrayAdapter<>(ListItemsActivity.this, android.R.layout.simple_list_item_1, listItems);
        itemsListView.setAdapter(newItemAdapter);




        testIntent = getIntent();
        if(testIntent == null){
            Log.d("MAIN", "+++NULLL++++++"+testIntent);
        }else{
            listSentFromMain = testIntent.getStringArrayListExtra(MainActivity.DATA_KEY);
            testArrayList = listSentFromMain;
            if(listSentFromMain != null){
                String titleFromMain = listSentFromMain.get(listSentFromMain.size()-1);
                inputListTitle.setText(titleFromMain);
                listSentFromMain.remove(listSentFromMain.size() - 1);
                listItems = listSentFromMain;
                newItemAdapter.addAll(listItems);
                newItemAdapter.notifyDataSetChanged();
                Log.d("MAIN", "+++++++++NOTNULL"+ listItems + "-----" + testArrayList.size());
            }
        }


//        data = getIntent();
//        if(data == null){
//            listItems.addAll(data.getStringArrayListExtra(MainActivity.DATA_KEY));
//        }



        returnHomeButton();

        addItemToListButton();

        itemClickToDescription();

        longItemClickStrikeThrough();
    }

    private void initializeActivity(){
        itemsListView = (ListView)findViewById(R.id.list_item_listview);
        addItemButton = (FloatingActionButton)findViewById(R.id.list_item_addList_button);
        inputListTitle = (EditText)findViewById(R.id.list_item_title_edittext);
        inputListItem = (EditText)findViewById(R.id.list_item_input_edittext);

        tempButton = (Button)findViewById(R.id.list_temp_button);
    }

    private boolean checkListTitleInput(){
        return inputListTitle.getText().toString().isEmpty();
    }

    private boolean checkListItemInput(){
        return inputListItem.getText().toString().isEmpty();
    }

    private void returnHomeButton() {
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkListTitleInput()) {
                    Toast.makeText(ListItemsActivity.this, "Please enter a list title", Toast.LENGTH_SHORT).show();
                } else {

                    // Append title to bottom of our list
                    listItems.add(inputListTitle.getText().toString());

                    Log.d("MAIN", "TTTTTTTTTTBUTTOM TO HOME"+listItems);

                    Bundle listBundle = new Bundle();
                    listBundle.putStringArrayList("returnList", listItems);


                    data = new Intent();
                    //data.putExtra("result", inputListTitle.getText().toString());  //Send String "Surprise" with key to "result"
                    data.putExtra("result", listBundle);
                    setResult(RESULT_OK, data);
                    finish();   // Go to garbage collecting and remove from memory
                }
            }
        });
    }

    private void addItemToListButton(){
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addNewItem = inputListItem.getText().toString();

                if (checkListItemInput()) {
                    Toast.makeText(ListItemsActivity.this, "Please input an item", Toast.LENGTH_SHORT).show();
                } else {
                    inputListItem.getText().clear();
                    listItems.add(addNewItem);
                    Log.d("MAIN", "PpPPPPPPPPP----" + listItems);
                    newItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void itemClickToDescription(){
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentItem = (TextView) view;
                currentItem.setText("Hello");
            }
        });
    }

    private void longItemClickStrikeThrough() {
        itemsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView currentItem = (TextView) view;
                strikeThroughTextView(currentItem);
                return false;
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



//
//    // ++===== DOES NOT WORK =====//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == MainActivity.REQUEST_CODE){
//
//            if(resultCode == RESULT_OK){
//                Bundle sentBundle = data.getBundleExtra("sendList");
//                ArrayList<String> testList = sentBundle.getStringArrayList("sendList");
//
//                newItemAdapter.addAll(testList);
//                newItemAdapter.notifyDataSetChanged();
//            }
//        }
//    }
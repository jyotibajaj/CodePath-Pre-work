package letsdecode.com.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ItemListActivity extends AppCompatActivity {
    ArrayList<String> items = new ArrayList<>();
    ListView item_listView;
    ArrayAdapter<String> itemsAdapter;
    int savedPositionOfEdittedItem;
    private static final String TAG = ItemListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        //initializing list view
        item_listView = (ListView) findViewById(R.id.listView_items);
        readItems();
        //initializing arrayadapter
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        item_listView.setAdapter(itemsAdapter);
        Log.d(TAG, "onCreate: Items list" + items);
        setUpListViewLongListener();
        setUpListViewShortListener();

    }



    //method for AddItem button
    public void onAddItemClick(View view) {
        EditText editText_enterNewItem = (EditText) findViewById(R.id.editText_addItem);
        String newItemText = editText_enterNewItem.getText().toString();
        if (newItemText != null && newItemText.trim().isEmpty() == false) {
            itemsAdapter.add(newItemText);
            Log.d(TAG, "onAddItem: iTemTxt " + newItemText);
            editText_enterNewItem.setText("");
            writeItems();
        }

    }

    private void setUpListViewLongListener() {
        item_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "setupListViewLongListener. onItemLongClick: @@@@@@@" + position);
                Log.d(TAG, "onItemLongClick: removed" + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();

        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void setUpListViewShortListener() {
        item_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemFromArrayList = items.get(position);
                Log.d(TAG, "setupListViewShortListener. onItemLongClick: @@@@@@@" + position);
                Intent editActivityIntent = new Intent(ItemListActivity.this, EditActivity.class);
                savedPositionOfEdittedItem = position;
                editActivityIntent.putExtra("selectedItemText", selectedItemFromArrayList);
                startActivityForResult(editActivityIntent, 1);
            }
        });

    }

    // Call Back method  to get the editted text  form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 1
        if (requestCode == 1) {
            if (null != data) {
                // fetch the message String
                String textForList = data.getStringExtra("textFromEditText");
                if (textForList != null && textForList.trim().isEmpty() == false) {
                    items.set(savedPositionOfEdittedItem, textForList);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                }
            }
        }
    }
}





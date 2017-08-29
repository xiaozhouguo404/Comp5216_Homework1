package comp5216.sydney.edu.au.homework_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static comp5216.sydney.edu.au.homework_1.R.id.button;
import static comp5216.sydney.edu.au.homework_1.R.id.parent;

public class MainActivity extends AppCompatActivity {

    public final int EDIT_ITEM_REQUEST_CODE = 647;
    //define variables
    ListView listview;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //use "activity_main.xml" as the layout
        setContentView(R.layout.activity_main);
        //reference the "listview" variable to the id-"listview" in the layout
        listview = (ListView)findViewById(R.id.listview);
        //create an ArrayList of String
        items = new ArrayList<String>();
        items.add("item one");
        items.add("item two");

//        ToDoItem item1 = new ToDoItem("item 1");
//        item1.save();
//        ToDoItem item2 = new ToDoItem("item 2");
//        item2.save();

//        saveItemsToDatabase();
        readItemsFromDatabase();
//        Log.i("tag",items.toString());
        //Create an adapter for the list view using Android's built-in item layout
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        //connect the listview and the adapter
        listview.setAdapter(itemsAdapter);
        setupListViewListener();

        //change to add_edit_items.xml
        Button button =(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditToDoItemActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,EDIT_ITEM_REQUEST_CODE);
            }
        });


    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Extract name value from result extras
                String editedItem = data.getExtras().getString("item");
                int position = data.getIntExtra("position", -1);
//                Date date = new Date();
                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
                String a1=dateformat1.format(new Date());
//                String dateTime = date.toString();
//                String myString = DateFormat.getDateInstance().format(dateTime);

                if (position == -1){

                    items.add(editedItem+" ("+a1+")");

                }
                else {
                    items.set(position, editedItem+" ("+a1+")");
                    Log.i("Updated Item in list:", editedItem + ",position:"
                            + position);
                    Toast.makeText(this, "updated:" + editedItem, Toast.LENGTH_SHORT).show();

                }

                itemsAdapter.notifyDataSetChanged();

                saveItemsToDatabase();
            }
        }
     }


    private void setupListViewListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long rowId) {
                Log.i("MainActivity", "Long Clicked item " + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //delete the item
                                items.remove(position);
                                itemsAdapter.notifyDataSetChanged();
                                saveItemsToDatabase();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //User cancelled the dialog
                                //nothing happens
                            }
                        });
                builder.create().show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String updateItem = (String) itemsAdapter.getItem(position);
                Log.i("MainActivity", "Clicked item " + position + ": " + updateItem);
                Intent intent = new Intent(MainActivity.this, EditToDoItemActivity.class);
                if (intent != null) {
                    // put "extras" into the bundle for access in the edit activity
                    intent.putExtra("item", updateItem);
                    intent.putExtra("position", position);
                    // brings up the second activity
                    startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
                    itemsAdapter.notifyDataSetChanged();
                }
            }

        });

    }


    // database read
    private void readItemsFromDatabase() {
        //read items from database

        List<ToDoItem> itemsFromORM = ToDoItem.listAll(ToDoItem.class);
        items = new ArrayList<String>();
        if (itemsFromORM != null & itemsFromORM.size() > 0) {
            for (ToDoItem item : itemsFromORM) {
                items.add(item.todo);
            }
        }
    }

    // database update
    private void saveItemsToDatabase() {
        ToDoItem.deleteAll(ToDoItem.class);
        for (String todo : items) {
            ToDoItem item = new ToDoItem(todo);
            item.save();
            Log.i("SQL saved item: ", todo);
        }
    }


}


package comp5216.sydney.edu.au.homework_1;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class EditToDoItemActivity extends Activity {
    public int position = 0;
    EditText edItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //populate the screen using the layout
        setContentView(R.layout.add_edit_items);

        //Get the data from the main screen
        String editItem = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", -1);

        // show original content in the text field
        edItem = (EditText) findViewById(R.id.EditItem);
        edItem.setText(editItem);




        Button cancelbutton =(Button)findViewById(R.id.CancelEdit);


        cancelbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(EditToDoItemActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_edit_message)
                        .setTitle(R.string.dialog_edit_title)
                        .setPositiveButton(R.string.dialog_edit_item,new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Intent intent = new Intent();
                                intent.setClass(EditToDoItemActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel,new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                builder.create().show();
            }
        });


//        Button submitButton =(Button)findViewById(R.id.SaveEdit);
//
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                edItem = (EditText) findViewById(R.id.EditItem);
//                ToDoItem item = new ToDoItem(edItem.getText().toString());
//                item.save();
//                finish(); // closes the activity, pass data to parent
//            }
//        });

    }
    public void onSubmit(View v) {
        edItem = (EditText) findViewById(R.id.EditItem);

        // Prepare data intent for sending it back
        Intent data = new Intent();

        // Pass relevant data back as a result
        data.putExtra("item", edItem.getText().toString());
        data.putExtra("position",position);

//        ToDoItem item = new ToDoItem(edItem.getText().toString());
//        item.save();

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }


}

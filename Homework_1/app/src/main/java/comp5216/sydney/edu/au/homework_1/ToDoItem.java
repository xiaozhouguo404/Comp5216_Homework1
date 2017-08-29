package comp5216.sydney.edu.au.homework_1;

import android.widget.EditText;

import com.google.common.base.Strings;
import com.orm.SugarRecord;

import java.util.Date;

public class ToDoItem extends SugarRecord {
    public String todo;
//    public Date createTime;

    public ToDoItem(){}


    public ToDoItem(String ToDo){
        this.todo = ToDo;
    }
}

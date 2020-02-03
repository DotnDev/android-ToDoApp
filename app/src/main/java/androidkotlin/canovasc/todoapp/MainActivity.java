package androidkotlin.canovasc.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidkotlin.canovasc.todoapp.Utils.FileManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,TaskAdapter.OnTaskListener{

    EditText newTaskName_et;
    ImageButton saveTask_imgbtn;
    ArrayList<Task> mTaskArrayList = new ArrayList<>();
    TaskAdapter mTaskAdapter;
    RecyclerView mRecyclerView;
    String newTask;
    boolean isFirstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newTaskName_et = findViewById(R.id.mainactivity_newtask_et);
        saveTask_imgbtn = findViewById(R.id.mainactivity_newtask_save_imgbtn);

        saveTask_imgbtn.setOnClickListener(this);

        setRecyclerView();

    }


    //Display RecyclerView if tasks already exist
    public void setRecyclerView(){

        mTaskArrayList = FileManager.loadTasks(this);

        //Check if file already exists
        if(!mTaskArrayList.isEmpty()){
            isFirstTask = false;
            mTaskAdapter = new TaskAdapter(mTaskArrayList,this,this);
            mRecyclerView = findViewById(R.id.recyclerview);
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mTaskAdapter);

            //Initialise the swipe-to-delete
            ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
            new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView);

        }else{
            isFirstTask = true;
        }
    }



    //Swipe-to-delete on existing task
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        Task task = mTaskArrayList.get(viewHolder.getAdapterPosition());
        FileManager.deleteTask(this,task);
        mTaskArrayList.remove(viewHolder.getAdapterPosition());
        mTaskAdapter.notifyDataSetChanged();

    }

    //Click on existing task
    @Override
    public void onTaskClick(int position) {

    }


    //Click on + icon
    @Override
    public void onClick(View view) {

        newTask = newTaskName_et.getText().toString();

       if(!newTask.isEmpty()){
            Task task = new Task(newTask);
            FileManager.writeToFile(this,task);
            mTaskArrayList.add(task);

            if(isFirstTask){
                setRecyclerView();
            }else{
                mTaskAdapter.notifyDataSetChanged();
            }
            newTaskName_et.setText(null);


        }

    }


}

package com.deva.tidy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private final int ADD_TASK_REQUEST_CODE = 1;

    private ArrayList<Task> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myDataset = new ArrayList<Task>(3);
//        myDataset.add(new Task("Tugas 1", Timestamp.valueOf("2020-3-5 9:00:00")));
//        myDataset.add(new Task("Tugas 2", Timestamp.valueOf("2020-3-19 9:00:00")));
//        myDataset.add(new Task("Tugas 3", Timestamp.valueOf("2020-3-30 9:00:00")));

        myDataset = fetchAllTasks();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Collections.sort(myDataset);
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Task> fetchAllTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllTasks();
        if (c.moveToFirst())
        {
            do {
                String name = c.getString(1);
                String dueStr =  c.getString(2);
                boolean isDone = c.getInt(3) == 0 ? false : true;
                tasks.add(new Task(name, Timestamp.valueOf(dueStr), isDone, c.getInt(0)));
            } while (c.moveToNext());
        }
        db.close();
        return tasks;
    }

    public void onClick(View view){
        Intent i = new Intent("com.deva.tidy.AddTaskActivity");
        Bundle b = new Bundle();
        b.putInt("taskNumber", myDataset.size()+1);
        i.putExtras(b);
        startActivityForResult(i, ADD_TASK_REQUEST_CODE);
    }

    public void addTask(Task task){
        DBAdapter db = new DBAdapter(this);
        db.open();
        long id = db.insertTask(task.getName(), task.getDue(), task.isDone());
        db.close();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_TASK_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Task newTask = (Task) data.getSerializableExtra("newTask");
                addTask(newTask);
                myDataset.add(newTask);
                Collections.sort(myDataset);
                mAdapter = new MyAdapter(myDataset);
                recyclerView.setAdapter(mAdapter);
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        DBAdapter db = new DBAdapter(MainActivity.this);
        db.open();
        for(int i = 0; i < myDataset.size(); i++){
            Task t = myDataset.get(i);
            boolean status = db.updateTask((long) t.getId(), t.getName(), t.getDue(), t.isDone());
        }
        db.close();
    }

}

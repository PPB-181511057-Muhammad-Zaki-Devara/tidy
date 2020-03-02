package com.deva.tidy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.deva.tidy.dummy.DummyContent;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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

        myDataset = new ArrayList<Task>(3);
        myDataset.add(new Task("Tugas 1", Timestamp.valueOf("2020-3-5 9:00:00")));
        myDataset.add(new Task("Tugas 2", Timestamp.valueOf("2020-3-19 9:00:00")));
        myDataset.add(new Task("Tugas 3", Timestamp.valueOf("2020-3-30 9:00:00")));

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Collections.sort(myDataset);
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }

    public void onClick(View view){
        Intent i = new Intent("com.deva.tidy.AddTaskActivity");
        i.putExtra("taskNumber", myDataset.size());
        startActivityForResult(i, ADD_TASK_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_TASK_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Task newTask = (Task) data.getSerializableExtra("newTask");

                myDataset.add(newTask);
            }
        }
    }
}

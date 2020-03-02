package com.deva.tidy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import com.deva.tidy.Task;

public class AddTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        Toast.makeText(this, Integer.toString(getIntent().getIntExtra("taskNumber", 1)),
                Toast.LENGTH_SHORT).show();

    }

    public void onClick(View view){
        Intent i = new Intent();

        EditText taskName = (EditText) findViewById(R.id.taskName);
        EditText dueDate = (EditText) findViewById(R.id.dueDate);
        EditText dueTime = (EditText) findViewById(R.id.dueTime);

        Log.d("due date", dueDate.getText().toString());
        Log.d("due time", dueTime.getText().toString());

        String due = dueDate.getText().toString().replace('/', '-') + ' ' + dueTime.getText().toString();

        Task newTask = new Task( taskName.getText().toString(), Timestamp.valueOf(due));


        i.putExtra("newTask", newTask);

        setResult(RESULT_OK, i);
        finish();
    }
}

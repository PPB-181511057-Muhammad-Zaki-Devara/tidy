package com.deva.tidy;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Task[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout constraintLayout;
        public MyViewHolder(ConstraintLayout v){
            super(v);
            constraintLayout = v;
        }
    }

    public MyAdapter(Task[] myDataset){
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.d("Parent", parent.toString());
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        CheckBox task = (CheckBox)  holder.constraintLayout.getViewById(R.id.taskCheckBox);
        TextView dueDate = (TextView) holder.constraintLayout.getViewById(R.id.due_date);
        TextView dueTime = (TextView) holder.constraintLayout.getViewById(R.id.due_time);
        task.setText(mDataset[position].getName());
        dueDate.setText(new SimpleDateFormat("dd MMM yyyy").format(mDataset[position].getDue()));
        dueTime.setText(new SimpleDateFormat("hh:mm").format(mDataset[position].getDue()));
    }

    @Override
    public int getItemCount(){
        return mDataset.length;
    }

}

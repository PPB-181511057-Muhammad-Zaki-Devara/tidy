package com.deva.tidy;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Task> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout constraintLayout;
        public MyViewHolder(ConstraintLayout v){
            super(v);
            constraintLayout = v;
        }
    }

    public MyAdapter(ArrayList<Task> myDataset){
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
        task.setText(mDataset.get(position).getName());
        dueDate.setText(new SimpleDateFormat("dd MMM yyyy").format(mDataset.get(position).getDue()));
        dueTime.setText(new SimpleDateFormat("hh:mm").format(mDataset.get(position).getDue()));
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}

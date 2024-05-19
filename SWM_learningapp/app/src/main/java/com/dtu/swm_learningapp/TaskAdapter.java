package com.dtu.swm_learningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;
    private Context context;

    public TaskAdapter(ArrayList<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.todoText.setText(task.getTaskName());
        holder.todoCheckBox.setChecked(task.isDone());
        holder.priorityText.setText(task.getPriority());

        holder.todoCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
        });

        holder.deleteIcon.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                taskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        CheckBox todoCheckBox;
        TextView todoText;
        TextView priorityText;
        ImageView deleteIcon;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            todoCheckBox = itemView.findViewById(R.id.todoCheckBox);
            todoText = itemView.findViewById(R.id.todoText);
            priorityText = itemView.findViewById(R.id.priorityText);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}

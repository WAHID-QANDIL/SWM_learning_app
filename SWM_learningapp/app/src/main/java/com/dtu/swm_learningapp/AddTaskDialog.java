package com.dtu.swm_learningapp;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AddTaskDialog extends Dialog {

    private EditText newTaskText;
    private Spinner prioritySpinner;
    private Button newTaskButton;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;

    public AddTaskDialog(@NonNull Context context, TaskAdapter taskAdapter, ArrayList<Task> taskList) {
        super(context);
        this.taskAdapter = taskAdapter;
        this.taskList = taskList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_dialog);

        newTaskText = findViewById(R.id.newTaskText);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        newTaskButton = findViewById(R.id.newTaskButton);

        // Set up the priority spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = newTaskText.getText().toString();
                String priority = prioritySpinner.getSelectedItem().toString();
                if (!taskName.isEmpty()) {
                    taskList.add(new Task(taskName, priority));
                    taskAdapter.notifyDataSetChanged();
                    dismiss();
                }
            }
        });
    }
}

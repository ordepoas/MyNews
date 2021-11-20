package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aftebi.mynews.databinding.ActivityAddTaskBinding;
import com.aftebi.mynews.model.Priority;
import com.aftebi.mynews.model.Task;
import com.aftebi.mynews.service.AppDatabase;
import com.aftebi.mynews.service.AppExecutors;

public class AddTaskActivity extends AppCompatActivity {

    private ActivityAddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.normalRadioButton.setChecked(true);

        //---- on click
        binding.addTaskButton.setOnClickListener(view -> {
            String taskDesc = binding.addTaskEditText.getText().toString();
            if(taskDesc.length() == 0) {
                Toast.makeText(AddTaskActivity.this, "Campo vazio", Toast.LENGTH_SHORT)
                        .show();
            }else {
                Task task = new Task();
                task.setDescription(taskDesc);

                RadioButton r = findViewById(binding.addTaskRadioGroup.getCheckedRadioButtonId());
                String priority = r.getText().toString();
                Priority p;
                switch (priority) {
                    case "Urgente":
                        p = Priority.Urgent;
                        task.setPriority(p);
                        break;
                    case "Baixa":
                        p = Priority.Low;
                        task.setPriority(p);
                        break;
                    default:
                        p = Priority.Normal;
                        task.setPriority(p);
                }
                try {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                AppDatabase.getInstance(AddTaskActivity.this).getRepoDao().insertAll(task);
                            } catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(AddTaskActivity.this, "Erro ao adicionar à base de dados", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                   Log.e("JPSP", "Erro ao adicionar tarefa à base de dados -> "+ e.getMessage());
                } finally {
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
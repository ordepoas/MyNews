package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.aftebi.mynews.databinding.ActivityModifyTaskBinding;
import com.aftebi.mynews.model.Priority;
import com.aftebi.mynews.model.Task;
import com.aftebi.mynews.service.AppDatabase;
import com.aftebi.mynews.service.AppExecutors;

import java.io.IOException;

public class ModifyTaskActivity extends AppCompatActivity {

    private ActivityModifyTaskBinding binding;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //config init
        getSupportActionBar().setTitle("Modificar Tarefa");

        task = (Task) getIntent().getSerializableExtra("taskItem");

        binding.modifyTaskEditText.setText(task.getDescription());
        Priority p = task.getPriority();
                switch (p){
            case Urgent:
                binding.modifyUrgentRadioButton.setChecked(true);
                break;
            case Low:
                binding.modifyLowRadioButton.setChecked(true);
                break;
            default:
                binding.modifyNormalRadioButton.setChecked(true);
        }

        binding.modifyTaskButton.setOnClickListener(view -> {
            String newTask = binding.modifyTaskEditText.getText().toString();
            if(newTask.isEmpty() || newTask == "") {
                Toast.makeText(this, "O campo tarefa está vazio", Toast.LENGTH_SHORT).show();
            } else {
                task.setDescription(newTask);
                RadioButton r = findViewById(binding.modifyTaskRadioGroup.getCheckedRadioButtonId());
                String priority = r.getText().toString();
                Priority newP;
                switch (priority) {
                    case "Urgente":
                        newP = Priority.Urgent;
                        task.setPriority(newP);
                        break;
                    case "Baixa":
                        newP = Priority.Low;
                        task.setPriority(newP);
                        break;
                    default:
                        newP = Priority.Normal;
                        task.setPriority(newP);
                }
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            AppDatabase.getInstance(ModifyTaskActivity.this).getRepoDao().update(task);
                        } catch (Exception e){
                            Toast.makeText(ModifyTaskActivity.this, "Erro ao gravar na base de dados", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } finally {
                            finish();
                        }
                    }
                });
            }
        });

    }
}
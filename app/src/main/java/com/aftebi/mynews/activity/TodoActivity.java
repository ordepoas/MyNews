package com.aftebi.mynews.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aftebi.mynews.adapter.AdapterNews;
import com.aftebi.mynews.adapter.AdapterTask;
import com.aftebi.mynews.databinding.ActivityTodoBinding;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.Task;
import com.aftebi.mynews.service.AppDatabase;
import com.aftebi.mynews.service.AppExecutors;
import com.aftebi.mynews.service.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private ActivityTodoBinding binding;
    private AppDatabase dbLite;
    private List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("A fazer");
        getTasksFromDatabase();
        swipe();



        //---- on click
        binding.addTaskFAB.setOnClickListener(view ->  {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
        });

        binding.todoRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                binding.todoRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Task taskItem = tasks.get(position);
                        Intent intent = new Intent(TodoActivity.this, ModifyTaskActivity.class);
                        intent.putExtra("taskItem", taskItem);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Task taskItem = tasks.get(position);
                        deleteTask(taskItem);

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

    }

    public void getTasks(List<Task> tasks){

        //Config Recycler view
        //---- Config Adapter
        AdapterTask taskAdapter = new AdapterTask(tasks, TodoActivity.this);
        //---- Config Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.todoRecyclerView.setLayoutManager(layoutManager);
        binding.todoRecyclerView.setHasFixedSize(true);
        binding.todoRecyclerView.setAdapter(taskAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
            getTasksFromDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void getTasksFromDatabase(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            dbLite = AppDatabase.getInstance(TodoActivity.this);
            try{
                tasks = dbLite.getRepoDao().getAll();
                if(tasks.isEmpty()) {
                    Toast.makeText(TodoActivity.this, "Não existem tarefas criadas", Toast.LENGTH_SHORT)
                            .show();
                } else {

                }
            } catch (Exception e){
                Log.e("JPSP", e.getMessage());
            }
            runOnUiThread(() -> {
                getTasks(tasks);
            });
        });
    }

    public void deleteTask(Task task){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TodoActivity.this);
        alertDialog.setTitle("Eliminar");
        alertDialog.setMessage("Tem a certeza que deseja excluir o item?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(TodoActivity.this).getRepoDao().delete(task);
                        getTasksFromDatabase();
                    }
                });
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(TodoActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    public void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(0,swipeFlag);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                isDone(viewHolder);

            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(binding.todoRecyclerView);
    }

    public void isDone(RecyclerView.ViewHolder viewHolder){

        int position = viewHolder.getAdapterPosition();
        Task t = tasks.get(position);

        if(t.isDone()){
            t.setDone(false);
            Toast.makeText(TodoActivity.this, "A fazer", Toast.LENGTH_SHORT).show();
        } else {
            t.setDone(true);
            Toast.makeText(TodoActivity.this, "Feito", Toast.LENGTH_SHORT).show();

        }

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(TodoActivity.this).getRepoDao().update(t);
                getTasksFromDatabase();
            }
        });
    }

}
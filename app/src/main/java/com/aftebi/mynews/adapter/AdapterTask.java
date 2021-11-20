package com.aftebi.mynews.adapter;

import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.Task;

import java.util.List;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.MyViewHolder> {

    List<Task> _tasks;
    Context _context;

    public AdapterTask(List<Task> tasks, Context context) {
        _tasks = tasks;
        _context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_todo, parent, false);
        return new MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task t = _tasks.get(position);

        holder.todoTextView.setText(t.getDescription());
        switch (t.getPriority()){
            case Low:
                int green = holder.todoBarButton.getResources().getColor(R.color.green);
                holder.todoBarButton.setBackgroundColor(green);
                break;
            case Urgent:
                int red = holder.todoBarButton.getResources().getColor(R.color.red);
                holder.todoBarButton.setBackgroundColor(red);
                break;
            default:
                int yellow = holder.todoBarButton.getResources().getColor(R.color.yellow);
                holder.todoBarButton.setBackgroundColor(yellow);
        }

        if(t.isDone()) {
            holder.taskViewLayout.setBackground(holder.taskViewLayout.getResources().getDrawable(R.drawable.layout_line));
            int grey = holder.todoBarButton.getResources().getColor(R.color.task_completed);
            holder.todoBarButton.setBackgroundColor(grey);
        }

    }


    @Override
    public int getItemCount() {
        return _tasks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView todoTextView;
        Button todoBarButton;
        LinearLayout taskViewLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            todoTextView = itemView.findViewById(R.id.todoTextView);
            todoBarButton = itemView.findViewById(R.id.todoBarButton);
            taskViewLayout = itemView.findViewById(R.id.taskViewLayout);
        }

    }

}

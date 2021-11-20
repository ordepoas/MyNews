package com.aftebi.mynews.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.model.Zapping;

import java.util.List;

public class AdapterZapping extends RecyclerView.Adapter<AdapterZapping.MyViewHolder>{
    List<Zapping> zappingList;
    Context context;

    public AdapterZapping(List<Zapping> zappingList, Context context) {
        this.zappingList = zappingList;
        this.context = context;
    }

    @Override
    public AdapterZapping.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_zapping, parent, false);
        return new AdapterZapping.MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(AdapterZapping.MyViewHolder holder, int position) {
        Zapping z = zappingList.get(position);

        holder.titleZapping.setText(z.getTitle());

    }


    @Override
    public int getItemCount() {
        return zappingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleZapping;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleZapping = itemView.findViewById(R.id.zappingTitleTextView);
        }

    }
}

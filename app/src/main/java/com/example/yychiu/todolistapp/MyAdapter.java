package com.example.yychiu.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yychiu on 2017/11/28.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Todostuff> todostuffs;
    public MyAdapter(List<Todostuff> todostuffs){
        this.todostuffs=todostuffs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_event,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todostuff todostuff = todostuffs.get(position);

        holder.Content.setText(todostuff.getInfo());
        holder.Date.setText(todostuff.getDate());
    }

    @Override
    public int getItemCount() {
        return todostuffs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView Content ;
        private final TextView Date;
        public ViewHolder(View itemView){
            super(itemView);
            Content = itemView.findViewById(R.id.content);
            Date = itemView.findViewById(R.id.date);
        }
    }

    public void removeData(int position){
        todostuffs.remove(position);
        notifyItemRemoved(position);
    }
    public void updateData(int position){
        todostuffs.remove(position);
        notifyItemRemoved(position);
    }
}

package com.example.zubako.caliary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SchItemList  extends RecyclerView.Adapter<SchItemList.ViewHolder> {
    Context context;
    ArrayList<Schedule> schedules = new ArrayList<>();

    public SchItemList(Context context, ArrayList<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sch_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.title.setText(schedule.getTitle());
        holder.time.setText(schedule.getSch_time());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, time;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.sch_title);
            time = itemView.findViewById(R.id.sch_time);
        }
    }
}

package com.sams.samsqr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<AttendanceLog> attendanceLogList;
    RecyclerView recyclerView;
    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView timestap;
        TextView fullname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestap = itemView.findViewById(R.id.recycler0_item_timestamp);
            fullname = itemView.findViewById(R.id.recycler0_item_Name);
        }
    }

    public RecyclerViewAdapter(Context context, List<AttendanceLog> attendanceLogList, RecyclerView recyclerView){
        this.context = context;
        this.attendanceLogList = attendanceLogList;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycleview_0,parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        AttendanceLog attendanceLog = attendanceLogList.get(position);
        holder.fullname.setText(""+attendanceLog.getFullname());
        holder.timestap.setText(""+attendanceLog.getTimestamp("s"));
    }

    @Override
    public int getItemCount() {
        return attendanceLogList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = recyclerView.getChildLayoutPosition(view);
            String item = attendanceLogList.get(itemPosition).getFullname();
            //call Student profile in a new intent;
        }
    }
}

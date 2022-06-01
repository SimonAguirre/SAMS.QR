package com.sams.samsqr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<AttendanceLog> attendanceLogList;
    RecyclerView recyclerView;
    int[] avatars;
    static RecyclerViewClickListener listener;

    public RecyclerViewAdapter(Context context, List<AttendanceLog> attendanceLogList, RecyclerViewClickListener listener, int[] avatars){
        this.context = context;
        this.attendanceLogList = attendanceLogList;
        this.listener = listener;
        this.avatars = avatars;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycleview_0,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        AttendanceLog attendanceLog = attendanceLogList.get(position);
        holder.fullname.setText("" + attendanceLog.getFullname());
        holder.timestamp.setText("Time-in: " + attendanceLog.getTime());
        Random rand = new Random();
        holder.avatarImage.setImageResource(avatars[rand.nextInt(6)]);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return attendanceLogList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int pos);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView timestamp;
        TextView fullname;
        ImageView avatarImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.recycler0_item_timestamp);
            fullname = itemView.findViewById(R.id.recycler0_item_Name);
            avatarImage = itemView.findViewById(R.id.recycle0_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }



}





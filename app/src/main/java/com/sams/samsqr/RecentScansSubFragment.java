package com.sams.samsqr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentScansSubFragment extends Fragment {
    @BindView(R.id.records_today_recycleview)
    List<AttendanceLog> attendanceLogList;
    RecyclerViewAdapter.RecyclerViewClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_scans, container, false);
        //Bind the activity to ButterKnife
        ButterKnife.bind(getActivity());
        int[] avatars = {
                R.drawable.avatar_1,R.drawable.avatar_2,R.drawable.avatar_3,
                R.drawable.avatar_4,R.drawable.avatar_5,R.drawable.avatar_6
        };

        //Create Database Adapter
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),getString(R.string.attendance_logs_database_name));

        //Create attendance log list
        attendanceLogList = databaseHelper.getAll();
//        attendanceLogList = databaseHelper.getAll(today+" 00:00:00",today+" 23:59:59");//format YYYY-MM-DD
        //Create recycle viewer UI instance
        RecyclerView recyclerView_recentScans = (RecyclerView)view.findViewById(R.id.records_today_recycleview);
        recyclerView_recentScans.setHasFixedSize(true);
        //Create layout manager for recycle viewer
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //Create recycle view adapter
        setOnClickListener();
        RecyclerViewAdapter recycleViewAdapter = new RecyclerViewAdapter(getActivity(),attendanceLogList,listener, avatars);
        //Create Recycler Item Decoration
        RecyclerItemDecoration recyclerItemDecoration = new RecyclerItemDecoration(getActivity(),getResources().getDimensionPixelSize(R.dimen.header_height),true,getSectionCallback(attendanceLogList));
        //Add the adapter and layout manager and item decoration to the recycle viewer
        recyclerView_recentScans.setLayoutManager(layoutManager);
        recyclerView_recentScans.setAdapter(recycleViewAdapter);
        recyclerView_recentScans.addItemDecoration(recyclerItemDecoration);

        return view;
    }
    private void setOnClickListener(){
        listener = new RecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Intent intent = new Intent(getContext(),StudentProfileSummary.class);
                intent.putExtra("username", attendanceLogList.get(pos).getFullname());
                startActivity(intent);
            }
        };
    }

    private RecyclerItemDecoration.SectionCallback getSectionCallback(final List<AttendanceLog> list)
    {
        return new RecyclerItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position==0;
            }

            @Override
            public String getSectionHeaderName(int pos) {
                return "Recent Scans";
            }
        };
    }
}
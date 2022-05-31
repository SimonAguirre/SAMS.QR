package com.sams.samsqr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class RecordsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout();
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        };

        NavigationBarView navigationTab = (NavigationBarView)view.findViewById(R.id.record_tabs);
        navigationTab.setOnItemSelectedListener(onItemSelectedListener);

        //Create Database Adapter
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),getString(R.string.attendance_logs_database_name));
        //Create attendance log list
        List<AttendanceLog> attendanceLogs = new ArrayList<>();
        attendanceLogs = databaseHelper.getAll();

        //Create recycle viewer UI instance
        RecyclerView recyclerView_recentScans = (RecyclerView)view.findViewById(R.id.records_today_recycleview);
        recyclerView_recentScans.setHasFixedSize(true);

        //Create layout manager for recycle viewer
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //Create recycle view adapter
        RecyclerViewAdapter attendanceLogsRecycleViewAdapter = new RecyclerViewAdapter(getActivity(),attendanceLogs,recyclerView_recentScans);

        //Add the adapter and layout manager to the recycle viewer
        recyclerView_recentScans.setLayoutManager(layoutManager);
        recyclerView_recentScans.setAdapter(attendanceLogsRecycleViewAdapter);

        return view;
    }

    private void setLayout() {
        TextView textureView = getActivity().findViewById(R.id.actionbar_textview);
        textureView.setText(R.string.main_fragment_records);
        RelativeLayout relativeLayout = getActivity().findViewById(R.id.gradientHolder);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_frame_in_records));
    }
}
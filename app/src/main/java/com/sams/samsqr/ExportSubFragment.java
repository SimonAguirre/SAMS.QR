package com.sams.samsqr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

public class ExportSubFragment extends Fragment {
    List<AttendanceLog> attendanceLogList;
    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (!result) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(BuildConfig.APPLICATION_ID)));
                        Toast.makeText(getActivity(),"Please Grant Permission to Write to Storage",Toast.LENGTH_LONG).show();
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export_2, container, false);
        Button daily_attendance_button = view.findViewById(R.id.export_daily_attendance);
        setupPermission();
        daily_attendance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ExportRecordDaily.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void setupPermission (){
        int perm = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (perm != PackageManager.PERMISSION_GRANTED){
            mPermissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
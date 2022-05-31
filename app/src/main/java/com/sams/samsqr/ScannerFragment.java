package com.sams.samsqr;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.Timer;
import java.util.TimerTask;

public class ScannerFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private CodeScanner mCodeScanner;
    private Dialog scanPopUp;

    private ActivityResultLauncher<String> mPermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (!result) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(BuildConfig.APPLICATION_ID)));
                        Toast.makeText(getActivity(),"Please Grant Permission to Use Camera",Toast.LENGTH_LONG).show();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        setLayout();
        setupPermission();

        //Create Database Helper
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(),getString(R.string.attendance_logs_database_name));

        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView, CodeScanner.CAMERA_FRONT);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                Log.d("onDecoded", "run: "+result.getText());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        if (validateInput(result.getText())){
                            AttendanceLog attendanceLog;
                            InputProcessor inputProcessor;
                            try{
                                inputProcessor = new InputProcessor(result.getText());
                                attendanceLog = new AttendanceLog(inputProcessor.getFull_name());
                                boolean success = databaseHelper.addOne(attendanceLog);
                                showDialog(success, inputProcessor.getFull_name());
                            } catch (Exception e){
                                showDialog(false,e.toString());
                            }
                        }else{
                            showDialog(false,"Invalid QR Code");
                        }
                    }
                });
            }
        });


        scannerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCodeScanner.startPreview();
            }
        });


        return view;
    }

    private void setLayout() {
        TextView textureView = getActivity().findViewById(R.id.actionbar_textview);
        textureView.setText(R.string.main_fragment_scanner);
        RelativeLayout relativeLayout = getActivity().findViewById(R.id.gradientHolder);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_frame_in_scanner));
    }


    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private boolean validateInput(String input) {
        Log.d("validateInput", "validateInput: "+R.string.QRValidationKey);
        return input.endsWith(getResources().getString(R.string.QRValidationKey));
    }


    private void showDialog(boolean isSuccess, String text) {
        TextView descriptionHolder;
        TextView titleHolder;
        ImageView statusHolder;
        RelativeLayout backgroundHolder;

        scanPopUp = new Dialog(getActivity());
        scanPopUp.setContentView(R.layout.dialog_scan_result);
        scanPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = scanPopUp.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        titleHolder = scanPopUp.findViewById(R.id.dialog_title);
        descriptionHolder = scanPopUp.findViewById(R.id.dialog_description);
        statusHolder = scanPopUp.findViewById(R.id.dialog_status_icon);
        backgroundHolder = scanPopUp.findViewById(R.id.dialog_background);

        if (isSuccess){
            statusHolder.setImageResource(R.drawable.check_gradient);
            descriptionHolder.setText(text);
            titleHolder.setText("Success");
            backgroundHolder.setBackground(getResources().getDrawable(R.drawable.bg_diaglog_success));
        } else {
            statusHolder.setImageResource(R.drawable.cross_gradient);
            descriptionHolder.setText("Please use the QR Code provided by the school");
            titleHolder.setText("Invalid QR Code!");
            titleHolder.setTypeface(null,Typeface.BOLD);
            backgroundHolder.setBackground(getResources().getDrawable(R.drawable.bg_dialog_error));
        }
        scanPopUp.setCancelable(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        scanPopUp.show();


        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                scanPopUp.dismiss();
                mCodeScanner.startPreview();
                t.cancel();
            }
        }, 5000);

    }

    public void setupPermission (){
        int perm = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (perm != PackageManager.PERMISSION_GRANTED){
            mPermissionResult.launch(Manifest.permission.CAMERA);
        }
    }
}

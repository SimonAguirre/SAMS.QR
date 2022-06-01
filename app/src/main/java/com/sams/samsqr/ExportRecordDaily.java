package com.sams.samsqr;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ExportRecordDaily extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_record_daily);
        Button export = findViewById(R.id.export_daily_attendance_button);

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create Database Adapter
                DatabaseHelper databaseHelper = new DatabaseHelper(ExportRecordDaily.this, getString(R.string.attendance_logs_database_name));
                String date_today = new SimpleDateFormat("MMM_dd_yyyy", Locale.getDefault()).format(new Date());
                boolean b = databaseHelper.export_csv(date_today+" 00:00:00", date_today+" 23:59:59");
                if (b) {
                    showDialog(true,"File exported on Device Downloads Folder");
                }else{
                    showDialog(false, "Error while exporting database");
                }
            }
        });
    }
    private void showDialog(boolean isSuccess, String text) {
        TextView descriptionHolder;
        TextView titleHolder;
        ImageView statusHolder;
        RelativeLayout backgroundHolder;

        Dialog scanPopUp = new Dialog(this);
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
            descriptionHolder.setText(text);
            titleHolder.setText("Failed");
            titleHolder.setTypeface(null, Typeface.BOLD);
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
                t.cancel();
            }
        }, 2000);

    }
}
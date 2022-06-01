package com.sams.samsqr;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentProfileSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_summary);
        TextView studentname = findViewById(R.id.student_name);

        String name = "name unavailable";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("username");
        }

        studentname.setText(name);

    }
}
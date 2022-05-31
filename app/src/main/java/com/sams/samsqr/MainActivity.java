package com.sams.samsqr;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    final String TAG = "MainActivity.java";

    NavigationBarView navigationBarView;
    HomeFragment homeFragment = new HomeFragment();
    RecordsFragment recordsFragment = new RecordsFragment();
    ScannerFragment scannerFragment = new ScannerFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, homeFragment).commit();
                return true;
            case R.id.records:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, recordsFragment).commit();
                return true;
            case R.id.qrscanner:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer, scannerFragment).commit();
                return true;
        }
        return false;
    }
}
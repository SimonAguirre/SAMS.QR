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

import com.google.android.material.navigation.NavigationBarView;

public class RecordsFragment extends Fragment implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView topNavigationBar;
    RecentScansSubFragment recentScansSubFragment = new RecentScansSubFragment();
    ExploreSubFragment exploreSubFragment = new ExploreSubFragment();
    ExportSubFragment exportSubFragment = new ExportSubFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        topNavigationBar = view.findViewById(R.id.record_tabs);
        topNavigationBar.setOnItemSelectedListener(this);
        topNavigationBar.setSelectedItemId(R.id.today);
        return view;
    }
    private void setLayout() {
        TextView textureView = getActivity().findViewById(R.id.actionbar_textview);
        textureView.setText(R.string.main_fragment_records);
        RelativeLayout relativeLayout = getActivity().findViewById(R.id.gradientHolder);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_frame_in_records));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.today:
                getChildFragmentManager().beginTransaction().replace(R.id.recordsFragmentContainer, recentScansSubFragment).commit();
                return true;
            case R.id.explore:
                getChildFragmentManager().beginTransaction().replace(R.id.recordsFragmentContainer, exploreSubFragment).commit();
                return true;
            case R.id.export:
                getChildFragmentManager().beginTransaction().replace(R.id.recordsFragmentContainer, exportSubFragment).commit();
                return true;
        }
        return false;
    }
}
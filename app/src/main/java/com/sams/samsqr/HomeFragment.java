package com.sams.samsqr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    private void setLayout() {
        TextView textureView = getActivity().findViewById(R.id.actionbar_textview);
        textureView.setText(R.string.main_fragment_home);
        RelativeLayout relativeLayout = getActivity().findViewById(R.id.gradientHolder);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.bg_frame_in_home));
    }

}
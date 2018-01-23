package com.appjam.assist.assist.data.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appjam.assist.assist.R;

/**
 * Created by gominju on 2017. 7. 6..
 */

public class Fragment_notactic extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_formation_no, container, false);
        return v;
    }
}

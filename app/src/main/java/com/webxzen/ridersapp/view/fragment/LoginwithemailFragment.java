package com.webxzen.ridersapp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxzen.ridersapp.R;


public class LoginwithemailFragment extends Fragment {


    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

    view=inflater.inflate(R.layout.loginwithemail, container, false);
        return view;
    }
}

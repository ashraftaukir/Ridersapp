package com.webxzen.ridersapp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseFragment;



public class SearchFragment extends BaseFragment {


    public SearchFragment() {

    }

View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.searchview, container, false);
        return view;

    }
}

package com.webxzen.ridersapp.view.shared;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;


public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void replaceFragment(Fragment fragment, String id, String oldId, int resId) {
        getFragmentManager()
                .beginTransaction()
                .replace(resId, fragment, id)
                .addToBackStack(oldId)
                .commit();
    }

}

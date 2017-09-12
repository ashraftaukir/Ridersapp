package com.webxzen.ridersapp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webxzen.ridersapp.R;


public class LoginwithemailFragment extends Fragment implements View.OnClickListener {


    View view;
    TextView forgotpass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.loginwithemail, container, false);
        initViews();
        initializeListener();

        return view;
    }

    private void initializeListener() {

        forgotpass.setOnClickListener(this);
    }

    private void initViews() {
        forgotpass = (TextView) view.findViewById(R.id.forgotpassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotpassword:
                gotoforgotpasswordPage();
                break;

            default:
                break;
        }
    }

    private void gotoforgotpasswordPage() {

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForgotpasswordFragment()).commit();

    }
}

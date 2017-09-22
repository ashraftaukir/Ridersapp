package com.webxzen.ridersapp.view.login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.view.home.HomeScreenActivity;
import com.webxzen.ridersapp.view.shared.Appinfo;
import com.webxzen.ridersapp.view.shared.BaseFragment;


public class LoginwithemailFragment extends BaseFragment implements View.OnClickListener {


    View view;
    TextView forgotpass;
    Button applogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.loginwithemail, container, false);
        initialization();
        initializeListener();
        return view;
    }

    private void initializeListener() {

        forgotpass.setOnClickListener(this);
        applogin.setOnClickListener(this);
    }

    private void initialization() {
        applogin = (Button) view.findViewById(R.id.apploginbtn);
        forgotpass = (TextView) view.findViewById(R.id.forgotpassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgotpassword:
                gotoForgotpasswordPage();
                break;
            case R.id.apploginbtn:
                gotohomePage();

            default:
                break;
        }
    }

    private void gotohomePage() {

        Intent i = new Intent(getActivity(), HomeScreenActivity.class);
        startActivity(i);
    }

    private void gotoForgotpasswordPage() {

        replaceFragment(new ForgotpasswordFragment(),
                Appinfo.FORGOTPASSWORD_FRAGMENT, Appinfo.LOGIN_WITH_EMAIL_FRAGMENT, R.id.fragment_container);

    }
}

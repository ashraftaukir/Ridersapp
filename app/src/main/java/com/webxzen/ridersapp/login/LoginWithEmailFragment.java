package com.webxzen.ridersapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseFragment;
import com.webxzen.ridersapp.home.HomeScreenActivity;
import com.webxzen.ridersapp.util.Appinfo;


public class LoginWithEmailFragment extends BaseFragment implements View.OnClickListener {


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
                gotoHomePage();

            default:
                break;
        }
    }

    private void gotoHomePage() {

        Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
        getActivity().finish();
        startActivity(intent);
    }

    private void gotoForgotpasswordPage() {

        replaceFragment(new ForgotpasswordFragment(),
                Appinfo.FORGOTPASSWORD_FRAGMENT, Appinfo.LOGIN_WITH_EMAIL_FRAGMENT, R.id.fragment_container);

    }
}

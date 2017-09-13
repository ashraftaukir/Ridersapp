package com.webxzen.ridersapp.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.webxzen.ridersapp.R;


public class LoginFragment extends Fragment implements View.OnClickListener {


    Button loginwithfbbtn, loginwitheamilbtn;
    TextView registrationtv, showinfo;
    LoginButton loginButton;
    CallbackManager callbackManager;


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.loginscreen, container, false);
        initialization();
        listeners();
        fbloginprocess();
        return view;
    }

    private void fbloginprocess() {

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                showinfo.setText("success");


            }

            @Override
            public void onCancel() {
                showinfo.setText("cancle");
            }

            @Override
            public void onError(FacebookException exception) {
                showinfo.setText("error");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void listeners() {
        loginwitheamilbtn.setOnClickListener(this);
        loginwithfbbtn.setOnClickListener(this);
        registrationtv.setOnClickListener(this);
    }

    private void initialization() {
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        showinfo = (TextView) view.findViewById(R.id.showinfo);
        loginwithfbbtn = (Button) view.findViewById(R.id.loginwithfb);
        loginwitheamilbtn = (Button) view.findViewById(R.id.loginwithemail);
        registrationtv = (TextView) view.findViewById(R.id.register);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginwithemail:
                gotologinwithEmailPage();
                break;

            case R.id.loginwithfb:
                Toast.makeText(getContext(), "comming soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.register:
                gotoregisterPage();
                break;

            default:
                break;
        }
    }

    private void gotoregisterPage() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).commit();

    }

    private void gotologinwithEmailPage() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginwithemailFragment()).commit();

    }
}
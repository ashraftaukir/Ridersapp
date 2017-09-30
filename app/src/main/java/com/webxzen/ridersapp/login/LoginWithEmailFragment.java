package com.webxzen.ridersapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.api.AuthAPI;
import com.webxzen.ridersapp.api.RetrofitService;
import com.webxzen.ridersapp.base.BaseFragment;
import com.webxzen.ridersapp.home.HomeScreenActivity;
import com.webxzen.ridersapp.model.AuthModel;
import com.webxzen.ridersapp.model.LoginModel;
import com.webxzen.ridersapp.util.Appinfo;
import com.webxzen.ridersapp.util.DBHelper;
import com.webxzen.ridersapp.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginWithEmailFragment extends BaseFragment implements View.OnClickListener {


    View view;
    TextView forgotpass;
    Button appLoginbtn;
    private AuthAPI authAPI;
    EditText email_et;
    EditText passwordet;

    public LoginWithEmailFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.loginwithemail, container, false);
        initialization();
        initializeListener();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authAPI = RetrofitService.createService(AuthAPI.class, getString(R.string.api_server_url),
                false);

    }

    private void initializeListener() {

        forgotpass.setOnClickListener(this);
        appLoginbtn.setOnClickListener(this);
    }

    private void initialization() {

        email_et = (EditText) view.findViewById(R.id.emailedittext);
        passwordet = (EditText) view.findViewById(R.id.passwordedittext);
        appLoginbtn = (Button) view.findViewById(R.id.apploginbtn);
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


        String emailAddress = email_et.getText().toString();
        String password = passwordet.getText().toString();

        if (emailAddress.isEmpty()) {
            Toast.makeText(getActivity(), "Empty email or password", Toast.LENGTH_SHORT).show();

        } else if (password.isEmpty() || (password.length()) < 4) {
            Toast.makeText(getActivity(), "Empty password!!! Password should be 4 character atleast",
                    Toast.LENGTH_SHORT).show();

        } else {

            if (isNetworkAvailable()) {

                dialogUtil.showProgressDialog();
                String deviceToken = Utils.getDeviceId(getActivity());
                Call<AuthModel> emailLogin = authAPI.emaillogin(Appinfo.CLIENT_ID, Appinfo.CLIENT_SECRET,
                        Appinfo.SCOPE, Appinfo.PLATFORM, deviceToken, emailAddress, password);

                emailLogin.enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {

                        if (dialogUtil != null) {

                            dialogUtil.dismissProgress();

                        }
                        if (response.isSuccessful()) {
                            if (response.body().success) {
                                LoginModel loginModel = response.body().data.login;
                                if (loginModel != null) {
//                                    if (DBHelper.saveLogin(loginModel)) {
                                        startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                                        getActivity().finish();
//                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        if (dialogUtil != null) {
                            dialogUtil.dismissProgress();
                        }
                        Toast.makeText(getActivity(), "Testing error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                dialogUtil.showDialogOk(getString(R.string.no_internet));
            }
        }
    }

    private void gotoForgotpasswordPage() {

        replaceFragment(new ForgotpasswordFragment(),
                Appinfo.FORGOTPASSWORD_FRAGMENT, Appinfo.LOGIN_WITH_EMAIL_FRAGMENT, R.id.fragment_container);

    }
}

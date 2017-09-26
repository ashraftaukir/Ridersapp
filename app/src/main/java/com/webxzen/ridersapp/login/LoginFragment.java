package com.webxzen.ridersapp.login;

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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.api.AuthAPI;
import com.webxzen.ridersapp.api.RetrofitService;
import com.webxzen.ridersapp.base.BaseFragment;
import com.webxzen.ridersapp.home.HomeScreenActivity;
import com.webxzen.ridersapp.model.AuthModel;
import com.webxzen.ridersapp.model.LoginModel;
import com.webxzen.ridersapp.util.Appinfo;
import com.webxzen.ridersapp.util.Utils;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment implements View.OnClickListener {


    Button loginwithfbbtn, loginwitheamilbtn;
    TextView registrationtv;
    CallbackManager callbackManager;
    private AuthAPI authAPI;


    public LoginFragment() {

    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        view = inflater.inflate(R.layout.loginscreen, container, false);

        initialization();
        listeners();
        fbloginprocess();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authAPI = RetrofitService.createService(AuthAPI.class, getString(R.string.api_server_url),
                false);

    }

    private void fbloginprocess() {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

               // ApiCall(loginResult.getAccessToken().getToken());

                Intent i = new Intent(getActivity(), HomeScreenActivity.class);
                startActivity(i);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

    }

    private void ApiCall(String fbaccessToken) {

        if (isNetworkAvailable()) {

            dialogUtil.showProgressDialog();
            String deviceToken = Utils.getDeviceId(getActivity());
            Call<AuthModel> fblogin = authAPI.fblogin(Appinfo.CLIENT_ID, Appinfo.CLIENT_SECRET,
                    Appinfo.SCOPE,fbaccessToken,  Appinfo.PLATFORM, deviceToken);

            fblogin.enqueue(new Callback<AuthModel>() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void listeners() {
        loginwitheamilbtn.setOnClickListener(this);
        loginwithfbbtn.setOnClickListener(this);
        registrationtv.setOnClickListener(this);
    }

    private void initialization() {
        loginwitheamilbtn = (Button) view.findViewById(R.id.loginwithemail);
        loginwithfbbtn = (Button) view.findViewById(R.id.loginwithfb);
        registrationtv = (TextView) view.findViewById(R.id.register);
        // testtv = (TextView) view.findViewById(R.id.testtv);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginwithemail:
                gotoLoginwithEmailPage();
                break;

            case R.id.loginwithfb:

                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));

                break;

            case R.id.register:
                gotoRegisterPage();
                break;

            default:
                break;
        }
    }

    private void gotoRegisterPage() {
        replaceFragment(new RegistrationFragment(),
                Appinfo.REGISTER_FRAGMENT, Appinfo.LOGIN_FRAGMENT, R.id.fragment_container);

    }

    private void gotoLoginwithEmailPage() {
        replaceFragment(new LoginWithEmailFragment(),
                Appinfo.LOGIN_WITH_EMAIL_FRAGMENT, Appinfo.LOGIN_FRAGMENT, R.id.fragment_container);

    }
}
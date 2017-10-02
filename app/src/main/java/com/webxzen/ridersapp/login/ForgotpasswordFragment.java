package com.webxzen.ridersapp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    View view;
    EditText emailorphonenumber;
    Button sendbtn;
    TextInputLayout textInputLayoutemail;
    private AuthAPI authAPI;

    public ForgotPasswordFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword, container, false);
        initialization();
        initListeners();
        return view;

    }

    private void initListeners() {

        sendbtn.setOnClickListener(this);

    }

    private void initialization() {
        emailorphonenumber = (EditText) view.findViewById(R.id.emailorphoneinfo);
        sendbtn = (Button) view.findViewById(R.id.sendbutton);
        textInputLayoutemail = (TextInputLayout) view.findViewById(R.id.emailorphoneet);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authAPI = RetrofitService.createService(AuthAPI.class, getString(R.string.api_server_url),
                false);
    }

    private void gotoValidationProcess() {
        String emailorphone = emailorphonenumber.getText().toString();
        if (isValidEmail(emailorphone) || (!emailorphone.isEmpty())) {

            ApiCall(emailorphone);

            //    Toast.makeText(getActivity(), "Sending you message", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Check your email or phonenumber", Toast.LENGTH_SHORT).show();
        }
    }

    private void ApiCall(String emailorphone) {

        if (isNetworkAvailable()) {

            dialogUtil.showProgressDialog();
            String deviceToken = Utils.getDeviceId(getActivity());
            Call<AuthModel> forgetPassword = authAPI.forgetPassword(emailorphone);

            forgetPassword.enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {

                    if (dialogUtil != null) {

                        dialogUtil.dismissProgress();

                    }
                    if (response.isSuccessful()) {
                        if (response.body().success) {
                            String msg = response.body().message;
                            dialogUtil.showDialogOk(msg, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    replaceFragment(new LoginWithEmailFragment(),
                                            Appinfo.LOGIN_WITH_EMAIL_FRAGMENT,
                                            Appinfo.FORGOTPASSWORD_FRAGMENT,R.id.fragment_container);
                                }
                            });

                        }
                        else{
                            Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_LONG).show();

                        }

                    }
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
                    if (dialogUtil != null) {
                        dialogUtil.dismissProgress();
                    }
                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            dialogUtil.showDialogOk(getString(R.string.no_internet));
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sendbutton:
                gotoValidationProcess();
                break;

            default:
                break;
        }
    }


}

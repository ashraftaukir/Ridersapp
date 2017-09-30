package com.webxzen.ridersapp.login;

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
import com.webxzen.ridersapp.base.BaseFragment;


public class ForgotpasswordFragment extends BaseFragment implements View.OnClickListener{

    View view;
    EditText emailorphonenumber;
    Button sendbtn;
    TextInputLayout textInputLayoutemail;


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



    private void gotoValidationProcess() {
        String emailorphone = emailorphonenumber.getText().toString();
        if (isValidEmail(emailorphone) || (emailorphone.length() == 11)) {

            Toast.makeText(getActivity(), "Sending u message", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Check your email or phonenumber", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sendbutton:
                gotoValidationProcess();
                break;

            default:
                break;


        }
    }

}
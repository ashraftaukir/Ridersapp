package com.webxzen.ridersapp.login;

import android.app.Fragment;
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
import com.webxzen.ridersapp.home.HomeScreenActivity;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    View view;
    Button signupbtn;
    EditText fullname, email, phonenumber, password;
    TextInputLayout textInputLayoutfullname, textInputLayoutemailaddress,
            textInputLayoutphonenumber, textInputLayoutpassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.registrationscreen, container, false);
        initialization();
        initiaLizeListener();
        return view;
    }

    private void initiaLizeListener() {
    signupbtn.setOnClickListener(this);

    }

    private void initialization() {
        signupbtn = (Button) view.findViewById(R.id.signup);
        textInputLayoutfullname = (TextInputLayout) view.findViewById(R.id.fullnameet);
        textInputLayoutemailaddress = (TextInputLayout) view.findViewById(R.id.emailaddresset);
        textInputLayoutphonenumber = (TextInputLayout) view.findViewById(R.id.phoneinfoet);
        textInputLayoutpassword = (TextInputLayout) view.findViewById(R.id.passwordet);
        fullname = (EditText) view.findViewById(R.id.fullnameedittext);
        email = (EditText) view.findViewById(R.id.emailaddressedittext);
        phonenumber = (EditText) view.findViewById(R.id.phoneinfoedittext);
        password = (EditText) view.findViewById(R.id.passwordedittext);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signup:
                gotoValidationProcess();
                break;

            default:
                break;
        }

    }

    private void gotoValidationProcess() {

        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        if (!validatePhoneNumber()) {
            return;
        }

        gotoHomeScreenActivity();
    }

    private void gotoHomeScreenActivity() {

        Intent intent=new Intent(getActivity(),HomeScreenActivity.class);
        startActivity(intent);
    }

    private boolean validateName() {

        boolean driverfullname = fullname.getText().toString().trim().isEmpty();
        if (driverfullname) {
            // textInputLayoutfullname.setHintTextAppearance(R.style.Active);
            Toast.makeText(getActivity(), "Please select your full name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateEmail() {

        String driveremailaddress = email.getText().toString();
        if (driveremailaddress.isEmpty() || !isValidEmail(driveremailaddress)) {
            // textInputLayoutemailaddress.setHintTextAppearance(R.style.Active);
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePhoneNumber() {

        int driverphonenumber = phonenumber.getText().toString().trim().length();
        if (driverphonenumber<1) {
            //textInputLayoutphonenumber.setHintTextAppearance(R.style.Active);
            Toast.makeText(getActivity(), "Please select your phonenumber", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validatePassword() {

        int driverpassword = password.getText().toString().length();
        if (driverpassword < 4) {
            // textInputLayoutpassword.setHintTextAppearance(R.style.Active);
            Toast.makeText(getActivity(), "Atleast 4 character", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    private void gotologinPage() {
//
//        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new LoginwithemailFragment()).addToBackStack(null).commit();
//    }


    @Override
    public void onResume() {
        super.onResume();
        fullname.setText("");
        email.setText("");
        phonenumber.setText("");
        password.setText("");

    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

package com.webxzen.ridersapp.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseFragment;

import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class SearchFragment extends BaseFragment {


    View view;
    Double lat,lon;




    @SuppressLint("ValidFragment")
    public SearchFragment(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

//    Location lat,lon;

    String locationaddress;
    EditText currentposition_et,destination_et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchview, container, false);
        getLocationAddress();
        initialization();
        return view;

    }

    private void initialization() {
        destination_et=(EditText)view.findViewById(R.id.destination_et);
        currentposition_et=(EditText)view.findViewById(R.id.currentposition_et);
        currentposition_et.setText(locationaddress);
        destination_et.requestFocus();
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
      //  InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       // imm.showSoftInput(destination_et, InputMethodManager.SHOW_IMPLICIT);


    }

    private void getLocationAddress() {

         locationaddress=getCompleteAddressString(lat,lon);

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
    String strAdd = "";
    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
        if (addresses != null) {
            Address returnedAddress = addresses.get(0);
            StringBuilder strReturnedAddress = new StringBuilder("");

            for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
            }
            strAdd = strReturnedAddress.toString();
            Log.d("Current loction ", strReturnedAddress.toString());
        } else {
            Log.d("Current loction", "No Address returned!");
        }
    } catch (Exception e) {
        e.printStackTrace();
        Log.w(" loction address", "Canont get Address!");
    }
            return strAdd;
}

}

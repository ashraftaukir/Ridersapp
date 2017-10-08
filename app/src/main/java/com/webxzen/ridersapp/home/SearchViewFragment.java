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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseFragment;
import com.webxzen.ridersapp.util.Appinfo;

import java.util.List;
import java.util.Locale;

import static com.facebook.login.widget.ProfilePictureView.TAG;



public class SearchViewFragment extends BaseFragment implements View.OnClickListener {


    View view;
    String fragmentname;
    ImageView backbutton;
    CallBackListener callBackListener;
    Context context;



    PlaceAutocompleteFragment autocompleteFragment, start_point_fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchview, container, false);
        String fragmentName = getArguments().getString(Appinfo.FRAGMENT_NAME);
        initialization();
        initListeners();
        return view;

    }

    private void initListeners() {


        backbutton.setOnClickListener(this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                Toast.makeText(getActivity(), "Testing", Toast.LENGTH_SHORT).show();
//                setCallBackListener(callBackListener);
//                if (callBackListener != null) {
//                    Log.d("onPlaceSelected", "onPlaceSelected:");
//                    callBackListener.callback(place.getName().toString());
//                    getActivity().finish();
//                }
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });


    }

    public void setCallBackListener(CallBackListener callback) {
        Log.d("setCallBackListener", "setCallBackListener:");
        this.callBackListener = callback;

    }

    private void initialization() {


        //callBackListener = (HomeScreenActivity) context;
        backbutton = (ImageView) view.findViewById(R.id.backbutton_icon);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().
                        findFragmentById(R.id.place_autocomplete_fragment);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.backbutton_icon:
                getActivity().finish();
                break;

            default:
                break;

        }
    }

}

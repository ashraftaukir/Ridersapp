package com.webxzen.ridersapp.home;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.model.AdvertisementModel;

import java.util.ArrayList;


public class HomeScreenActivity extends BaseActivity implements OnMapReadyCallback, LocationListener {


    RecyclerView cardListRecylerview;
    AdvertisementAdapter advertisementAdapter;
    ArrayList<AdvertisementModel> advertiseList = new ArrayList<>();
    AdvertisementModel advertisementModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.homescreenactivity);
        prepareAdevertisementModel();
        initialization();
    }

    private void prepareAdevertisementModel() {

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

    }


    private void initialization() {

        cardListRecylerview = (RecyclerView) findViewById(R.id.cardListRecylerview);
        advertisementAdapter = new AdvertisementAdapter(advertiseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardListRecylerview.setLayoutManager(linearLayoutManager);
        cardListRecylerview.setAdapter(advertisementAdapter);
        mapinitialization();
        bottomsheetinitialization();

    }

    private void bottomsheetinitialization() {


        View bottomSheet = findViewById(R.id.design_bottom_sheet_relativelayout);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        bottomSheet.setBackgroundColor(getResources().getColor(R.color.colorTransparentblack));

                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        bottomSheet.setBackgroundColor(Color.BLACK);

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        bottomSheet.setBackgroundResource(0);

                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        bottomSheet.setBackgroundResource(0);

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

    }

    private void mapinitialization() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)


        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(Location location) {


        // Log.d("onLocationChanged", "onLocationChanged: ");
        //double lat = location.getLatitude();
        //double lng = location.getLongitude();

    }
}

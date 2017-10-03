package com.webxzen.ridersapp.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.login.SplashScreenActivity;
import com.webxzen.ridersapp.model.AdvertisementModel;
import com.webxzen.ridersapp.util.Appinfo;
import com.webxzen.ridersapp.util.DBHelper;

import java.util.ArrayList;


public class HomeScreenActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, OnMapReadyCallback, View.OnClickListener {

    RecyclerView cardListRecylerview;
    AdvertisementAdapter advertisementAdapter;
    ArrayList<AdvertisementModel> advertiseList = new ArrayList<>();
    AdvertisementModel advertisementModel;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Toolbar toolbar;
    ImageView navdrawerIcon;
    ViewGroup header;
    LinearLayout searchBarLinearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreenactivity);
        setUpToolbar();
        prepareAdevertisementModel();
        initialization();
        initListeners();
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void initListeners() {

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        navdrawerIcon.setOnClickListener(this);
        searchBarLinearLayout.setOnClickListener(this);

    }

    private void prepareAdevertisementModel() {

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);
        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

    }


    private void initialization() {

        //toolbar item initialization
        navdrawerIcon = (ImageView) findViewById(R.id.navdrawer_icon);
        searchBarLinearLayout = (LinearLayout) findViewById(R.id.searchBarLinearlayout);

        String[] navDrawerTitles = getResources().getStringArray(R.array.string_array_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navdrawerlistitem, R.id.navitem_tv, navDrawerTitles));

        cardListRecylerview = (RecyclerView) findViewById(R.id.cardListRecylerview);
        advertisementAdapter = new AdvertisementAdapter(advertiseList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardListRecylerview.setLayoutManager(linearLayoutManager);
        cardListRecylerview.setAdapter(advertisementAdapter);
        navHeaderSetup();
        ratingBarSetup();
        mapinitialization();
        bottomsheetinitialization();

    }

    private void ratingBarSetup() {

        RatingBar ratingBar = (RatingBar) header.findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorWhite),
                PorterDuff.Mode.SRC_ATOP);
    }

    private void navHeaderSetup() {
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.navdrawerheader, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, " +
                                "please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(HomeScreenActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }




    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {

        Intent i;
        switch (position) {

            case 0:
                break;

            case 1:
                i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case 3:
                i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case 4:
                i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
            case 5:
                logout();
                break;

            default:
                break;

        }
    }


    private void logout() {
        DBHelper.remove(DBHelper.DB_LOGIN);
        startActivity(new Intent(HomeScreenActivity.this, SplashScreenActivity.class));
        finishAffinity();
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
                .findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.navdrawer_icon:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.searchBarLinearlayout:
//                 initFragment(new SearchFragment(), Appinfo.SEARCHFRAGMENT,R.id.frame_container);
                getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_up,
//                                R.anim.slide_out_up
//                                )

                        .add(R.id.frame_container, new SearchFragment(),Appinfo.SEARCHFRAGMENT)
                        .commit();

                break;
            default:
                break;

        }

    }
}

package com.webxzen.ridersapp.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.login.SplashScreenActivity;
import com.webxzen.ridersapp.model.AdvertisementModel;
import com.webxzen.ridersapp.util.Appinfo;
import com.webxzen.ridersapp.util.DBHelper;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class HomeScreenActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, OnMapReadyCallback,
        View.OnClickListener
        // ,CallBackListener
{
    private static final String TAG = "TEST";
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
    //  LinearLayout searchBarLinearLayout;
    TextView pickupAddress, dropupAddress, taxiprice;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_DROPUP = 1;
    public boolean googleActivityCallingTracker = false;
    public LatLng picUpLatLong;
    public LatLng dropUpLatLong;
    Button doneButton, confirm_btn;
    View bottomSheet;
    RelativeLayout bottomRelativeLayout;
    boolean confirmbuttonClickChecker = false;
    boolean cameraMoving = false;
    boolean mapAdding=false;
    ProgressBar progressbar;
    Marker dropUpMarker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreenactivity);
        setUpToolbar();
        prepareAdvertisementModel();
        initialization();
        initListeners();


    }

    private void setPickupLocation(LatLng latLng) {
        picUpLatLong = latLng;
        String address = getCompleteAddressString(latLng.latitude, latLng.longitude);
        pickupAddress.setText(address);
    }

    private void clearPickupLocation() {
        pickupAddress.setText("");
    }


    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void initListeners() {

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        navdrawerIcon.setOnClickListener(this);
        pickupAddress.setOnClickListener(this);
        dropupAddress.setOnClickListener(this);
        doneButton.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }


    private void prepareAdvertisementModel() {

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome),
                getResources().getString(R.string.advertisementheader),
                getResources().getString(R.string.advertisement));
        advertiseList.add(advertisementModel);

        advertisementModel = new AdvertisementModel(
                getResources().getString(R.string.welcome2),
                getResources().getString(R.string.advertisementheader2),
                getResources().getString(R.string.advertisement2));
        advertiseList.add(advertisementModel);

    }


    private void initialization() {

        //toolbar item initialization
        navdrawerIcon = (ImageView) findViewById(R.id.navdrawer_icon);
        doneButton = (Button) findViewById(R.id.done_btn);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        bottomRelativeLayout = (RelativeLayout) findViewById(R.id.bottom_RelativeLayout);
        taxiprice = (TextView) findViewById(R.id.taxiprice);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        // searchBarLinearLayout = (LinearLayout) findViewById(R.id.searchBarLinearlayout);

        String[] navDrawerTitles = getResources().getStringArray(R.array.string_array_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.navdrawerlistitem, R.id.navitem_tv, navDrawerTitles));


        pickupAddress = (TextView) findViewById(R.id.pickup_tv);
        dropupAddress = (TextView) findViewById(R.id.dropup_tv);


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

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        startLocationUpdates();

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
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        setPickupLocation(latLng);
        stopLocationUpdates();

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
        // TODO: Apply actual padding
        // please add here the top padding by calculating search text view's hieght
        // and action bar height and replace 450
        mGoogleMap.setPadding(0, 450, 0, 0);


        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {
                Log.d(TAG, "MoveStartedListener");
                if (!cameraMoving) {
                    Log.d(TAG, "onCameraMoveStarted: ");
                    clearPickupLocation();
                }

            }
        });


        //centerise marker in google map
        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                Log.d(TAG, "setOnCameraMoveListener");
                if (!cameraMoving) {
                    //Remove previous center if it exists
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }

                    LatLng position = mGoogleMap.getCameraPosition().target;
                    mCurrLocationMarker =
                            mGoogleMap.addMarker(new MarkerOptions().position(position).anchor(0.5f, 1.0f).title("Pickup"));

                }
            }
        });

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.d(TAG, "setOnCameraIdleListener");

                if (!cameraMoving) {
                    Log.d(TAG, "onCameraIdle:");
                    LatLng position = mGoogleMap.getCameraPosition().target;
                    setPickupLocation(position);
                }
            }
        });


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

//    @Override
//    public void callback(String address) {
//        dropupAddress.setText(address);
//    }


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


        bottomSheet = findViewById(R.id.design_bottom_sheet_relativelayout);
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


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {

                // strAdd=addresses.get(0).getLocality();
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strReturnedAddress.append(returnedAddress.getLocality()).append("\n");
                //   strReturnedAddress.append(address.getPostalCode()).append("\n");
                // strReturnedAddress.append(returnedAddress.getCountryName());
                strAdd = strReturnedAddress.toString();
            } else {
                Log.d("Current loction", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Log.w(" loction address", "Canont get Address!");
            //Log.d("loction_address", String.valueOf(e));
            Log.d(" loction address", "Canont get Address!", e);
        }
        return strAdd;
    }


    private void gotoGoogleActivity() {

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_DROPUP);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_DROPUP) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                if (!googleActivityCallingTracker) {

                    Log.d(TAG, "onActivityResult: ");
                    cameraMoving = true;
                    dropUpLatLong = place.getLatLng();
                    dropupAddress.setText(place.getAddress().toString());
                    //invisible bottom sheet and visible button
                    bottomSheet.setVisibility(View.GONE);
                    doneButton.setVisibility(View.VISIBLE);
                    mCurrLocationMarker.remove();
                    setDropPointAddressMarker();
                    setPicPointAddressMarker(0);
                    mapAdding=true;


                } else {
                    picUpLatLong = place.getLatLng();
                    pickupAddress.setText(place.getAddress().toString());
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("STATUS", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }


        }
    }

    private void setPicPointAddressMarker(int cameraUdpateValue) {


        if (cameraUdpateValue == 1) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(picUpLatLong));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }else {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(picUpLatLong);
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        }

    }

    private void setDropPointAddressMarker() {

        //dropUpaddress Marker added to the project
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(dropUpLatLong);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        dropUpMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(dropUpLatLong));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }

    private void setDropPointMarkerMover(){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(dropUpLatLong));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.navdrawer_icon:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.pickup_tv:

                if(mapAdding){
                    setPicPointAddressMarker(1);
                }else{
                    googleActivityCallingTracker = true;
                    gotoGoogleActivity();
                }
                break;
            case R.id.dropup_tv:

                if(mapAdding){
                    setDropPointMarkerMover();
                }else{
                    googleActivityCallingTracker = false;
                    gotoGoogleActivity();
                }
                break;
            case R.id.done_btn:

                if (!dropupAddress.getText().toString().isEmpty() && !pickupAddress.getText().toString().isEmpty()) {
                    if (isNetworkAvailable()) {
                        callDrawPloyLineApi(pickupAddress.getText().toString(), dropupAddress.getText().toString());
                    } else {
                        dialogUtil.showDialogOk(getString(R.string.no_internet));
                    }
                }
                break;
            case R.id.confirm_btn:


                if (!confirmbuttonClickChecker) {

                    //    dialogUtil.showProgressDialog();
                    confirm_btn.setText(getString(R.string.cancel));
                    confirmbuttonClickChecker = true;
                    progressbar.setVisibility(View.VISIBLE);

                } else {
                    progressbar.setVisibility(View.GONE);
                    //bottomRelativeLayout.setVisibility(View.GONE);
                    //doneButton.setVisibility(View.GONE);
                    //bottomSheet.setVisibility(View.VISIBLE);
                    finish();
                    startActivity(getIntent());
                }


                break;

            default:
                break;

        }

    }

    private void callDrawPloyLineApi(String pickup, String dropup) {
        Log.d(TAG, "callDrawPloyLineApi: ");
        DateTime now = new DateTime();
        try {

            //dialogUtil.showProgressDialog();
            DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.DRIVING).origin(pickup/*String.valueOf(picUpLatLong)pickupAddress.getText().toString()*/)
                    .destination(dropup/*String.valueOf(dropUpLatLong)dropupAddress.getText().toString()*/)
                    .departureTime(now)
                    .await();
            Log.d(TAG, "endDrawPloyLineApi: ");
            //dialogUtil.dismissProgress();

            addPolyline(result, mGoogleMap);
            addMarkersToMap(result, mGoogleMap);
            String value = "BDT";
            value = value + " " + String.valueOf(setTaxiPrice(result));
            taxiprice.setText(value);
            bottomRelativeLayout.setVisibility(View.VISIBLE);
            pickupAddress.setEnabled(false);
            dropupAddress.setEnabled(false);


        } catch (ApiException e) {

            e.printStackTrace();
            Log.d("ApiException", "Canont get Address!", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private int setTaxiPrice(DirectionsResult result) {

        String str = result.routes[0].legs[0].distance.humanReadable;
        str = str.replaceAll("[^\\d.]", "");
        Double kilometre = Double.valueOf(str) * 20;
        return kilometre.intValue();
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath =
                PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {

        mCurrLocationMarker.remove();
        dropUpMarker.remove();

        mMap.addMarker(new MarkerOptions().
                position(new LatLng(results.routes[0].legs[0].startLocation.lat,
                        results.routes[0].legs[0].startLocation.lng)).
                //icon(BitmapDescriptorFactory.fromBitmap(bmpicon)).
                        title(results.routes[0].legs[0].startAddress).snippet(getEndLocationTitle(results)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].
                legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).
                title(results.routes[0].legs[0].endAddress).snippet(getEndLocationTitle(results)));


        //show camera  bwtween bounds
        LatLng picupBound = new LatLng(results.routes[0].bounds.northeast.lat,
                results.routes[0].bounds.northeast.lng);
        LatLng dropupBound = new LatLng(results.routes[0].bounds.southwest.lat,
                results.routes[0].bounds.southwest.lng);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(picupBound);
        builder.include(dropupBound);
        LatLngBounds bounds = builder.build();
        int padding = 10; // offset from edges of the map in pixels
        CameraUpdate zoomlevel = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mGoogleMap.animateCamera(zoomlevel);

    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_map_api_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[0].legs[0].duration.humanReadable +
                " Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

}

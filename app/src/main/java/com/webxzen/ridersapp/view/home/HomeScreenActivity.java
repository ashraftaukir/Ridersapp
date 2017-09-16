package com.webxzen.ridersapp.view.home;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.webxzen.ridersapp.R;


public class HomeScreenActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.homescreenactivity);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bottomsheet bottomSheetDialogFragment = new Bottomsheet();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
      //  BottomSheetLayout bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
       // bottomSheet.showWithSheetView(LayoutInflater.from(getBaseContext()).inflate(R.layout.my_sheet_layout, bottomSheet, false));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        //  Toast.makeText(getApplicationContext(), "onmap", Toast.LENGTH_SHORT).show();

        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                //        .title("Marker in Sydney")


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

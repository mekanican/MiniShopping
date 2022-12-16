package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class AddressActivity extends AppCompatActivity {

    GoogleMap gm;
    Marker m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        gm = null;
        m = null;
        Geocoder gc = new Geocoder(this);
        TextInputLayout til = findViewById(R.id.text_box);
        til.setEndIconOnClickListener(view -> {
            if (gm == null) {
                return;
            }
            String addr = til.getEditText().getText().toString().trim();

            try {
                Address location = gc.getFromLocationName(addr, 1).get(0);
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                if (m != null) {
                    m.remove();
                }
                m = gm.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Your selected address"));
                gm.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 18));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(googleMap -> {
            gm = googleMap;
        });

        findViewById(R.id.elevatedButton).setOnClickListener(view -> {
            String addr = til.getEditText().getText().toString().trim();
            Toast.makeText(this, "Da xac nhan gui toi " + addr, Toast.LENGTH_LONG).show();
        });

    }
}
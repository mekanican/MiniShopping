package com.nlh.minishoping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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
            Address location = null;
            try {
                location = gc.getFromLocationName(addr, 1).get(0);
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng storePos = new LatLng(10.762417, 106.681198);
                float dist = distance((float) pos.latitude, (float) pos.longitude, (float) storePos.latitude, (float) storePos.longitude);
                int dayToDelivery = distanceToDay(dist);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, dayToDelivery);

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setDataAndType(CalendarContract.Events.CONTENT_URI, "vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "description zzz");
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis());
                intent.putExtra(CalendarContract.Events.TITLE, "Don hang 0x69");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "ABC");
                intent.putExtra(CalendarContract.Events.HAS_ALARM, true);
                startActivity(intent);

                Toast.makeText(this, "Da xac nhan gui toi " + addr + "\n" + "Khoang cach: " + dist + "\n" + "Giao trong " + dayToDelivery + " ngay", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    // https://stackoverflow.com/questions/8832071/how-can-i-get-the-distance-between-two-point-by-latlng
    // output km
    private float distance(float lat_a, float lng_a, float lat_b, float lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return ((float) distance * meterConversion) / 1000.f;
    }

    // Loc's heuristic <(")
    private int distanceToDay(float distance) {
        if (distance <= 100) {
            return 2;
        } else if (distance <= 1000) {
            // 100 -> 1000 : 3 -> 5, 100a + b = 3, 1000a + b = 5
            // 900a = 2 -> a = 1/450
            // 2/9 + b = 3 -> b = 25 / 9
            return (int) ((1.f / 450) * distance + (25.f / 9));
        } else {
            return (int) ((1.f / 150) * distance + (25.f / 9));
        }
    }
}
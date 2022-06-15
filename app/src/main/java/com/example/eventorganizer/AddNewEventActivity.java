package com.example.eventorganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;


public class AddNewEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TagToFilterTheLogcat";
    private EditText etEventName;
    private TextView tvDate2, tvTime2;
    private Button btnDate, btnTime, btnCancel, btnAdd;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        this.setTitle("Add new event");

        etEventName = findViewById(R.id.etEventName);
        tvDate2 = findViewById(R.id.tvDate2);
        btnDate = findViewById(R.id.btnDate);
        tvTime2 = findViewById(R.id.tvTime2);
        btnTime = findViewById(R.id.btnTime);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnAdd);


        //find map fragment by id
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        String apiKey = "AIzaSyDt5hfGaoTlEnukGtB3ed-rFGUPKnVpuEg";
        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(), apiKey);
        }

        PlacesClient placesClient = Places.createClient(AddNewEventActivity.this);


        //initialize autocomplete support fragment for location, find autocompletesupportfragment by id
        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            //set type filter to address
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ADDRESS);
            //location bias favors results within the specified geographical bounds
            //in this case, Novi Sad
        autocompleteSupportFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(41.980909,19.305685),
                new LatLng(46.534917, 20.742455)
        ));


            //set country
        autocompleteSupportFragment.setCountries("RS");

            //Place data fields define the types of Place data to return when requesting Place Details
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

            //on place selected listener
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occured" + status);
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                    //TODO (first create class Event)
            }
        });




    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
    }

    public void add(View v){
        try{
            //TODO create class Event, new instance here
            Toast.makeText(AddNewEventActivity.this,"Uspesno dodat dogadjaj!", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            //don't forget SHOW
            Toast.makeText(AddNewEventActivity.this, "Greska pri dodavanju dogadjaja!", Toast.LENGTH_LONG).show();
        }
    }

    public void cancel(View v){
        finish();
    }
}
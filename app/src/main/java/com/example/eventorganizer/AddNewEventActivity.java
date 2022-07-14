package com.example.eventorganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class AddNewEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "TagToFilterTheLogcat";
    private EditText etEventName;
    private TextView tvDate2, tvTime2;
    private Button btnDate, btnTime, btnCancel, btnAdd;
    private GoogleMap gMap;
    private EventItem event;

    //date picker
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        private TextView tvDate2;

        public void setView(TextView t){
            this.tvDate2 = t;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            month++;
            this.tvDate2.setText(day + "/" + month + "/" + year);
        }
    }

    //time picker
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        private TextView tvTime2;
        public void setView(TextView t){
            this.tvTime2 = t;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            //create a new instance of TimePickerDialog and return in
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            //TODO naci bolji nacin!!!
            if(minute <= 9) {
                this.tvTime2.setText(hourOfDay + ":0" + minute);
            }
            else{
                this.tvTime2.setText(hourOfDay + ":" + minute);
            }
        }
    }

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

    public void chooseDate(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setView(tvDate2);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void chooseTime(View v){
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setView(tvTime2);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void add(View v){
        try{
            event.setEventName(etEventName.getText().toString());
            event.setEventDate(tvDate2.getText().toString());
            event.setEventTime(tvTime2.getText().toString());
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
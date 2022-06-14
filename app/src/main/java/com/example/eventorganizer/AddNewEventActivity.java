package com.example.eventorganizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewEventActivity extends AppCompatActivity {

    private EditText etEventName;
    private TextView tvDate2, tvTime2;
    private Button btnDate, btnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        etEventName = findViewById(R.id.etEventName);
        tvDate2 = findViewById(R.id.tvDate2);
        btnDate = findViewById(R.id.btnDate);
        tvTime2 = findViewById(R.id.tvTime2);
        btnTime = findViewById(R.id.btnTime);

    }
}
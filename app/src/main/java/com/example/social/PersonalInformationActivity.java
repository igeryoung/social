package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PersonalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
    }

    public void AddPhoto(View view) {
    }

    public void Certain(View view) {

    }

    public void Cencal(View view) {
        finish();
    }
}

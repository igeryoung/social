package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PersonalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

    }

    public void AddPhoto(View view) {

        startSwipe();
    }

    public void Certain(View view) {

    }

    public void Cencal(View view) {
        finish();
    }

    public void startSwipe(){
        Intent next_page = new Intent(PersonalInformationActivity.this , TinderSwipe.class );
        startActivity(next_page);
    }
}

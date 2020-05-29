package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PersonalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

    }

    public void AddPhoto(View view) {
        Intent next_page = new Intent(PersonalInformationActivity.this , AddPhoto.class );
        startActivity(next_page);
    }

    public void Certain(View view) {
        EditText text_name = findViewById(R.id.name);
        String name = text_name.getText().toString();
        EditText text_gender = findViewById(R.id.gender);
        String gender = text_gender.getText().toString();
        EditText text_age = findViewById(R.id.age);
        String age = text_age.getText().toString();
        EditText text_college = findViewById(R.id.college);
        String college = text_college.getText().toString();
        EditText text_city = findViewById(R.id.city);
        String city = text_city.getText().toString();
        EditText text_about = findViewById(R.id.about);
        String about = text_about.getText().toString();

        startSwipe();
    }

    public void Cancel(View view) {
        finish();
    }

    public void startSwipe(){
        Intent next_page = new Intent(PersonalInformationActivity.this , TinderSwipe.class );
        startActivity(next_page);
    }

}

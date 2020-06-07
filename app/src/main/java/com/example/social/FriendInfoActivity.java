package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FriendInfoActivity extends AppCompatActivity {
    PersonalInformation PI;
    ImageButton ImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        Intent intent = getIntent();
        String info = intent.getStringExtra("friend_info");
        PI = new PersonalInformation(info);
        show();

    }

    private void show() {
        EditText text_name = findViewById(R.id.name);
        EditText text_gender = findViewById(R.id.gender);
        EditText text_age = findViewById(R.id.age);
        EditText text_college = findViewById(R.id.college);
        EditText text_city = findViewById(R.id.city);
        EditText text_about = findViewById(R.id.about);
        EditText text_interest = findViewById(R.id.interest);
        EditText text_personality = findViewById(R.id.interest);
        // " " put info of personalInfo get by id
        text_name.setText(PI.getName());
        text_gender.setText(PI.getGender());
        text_age.setText(PI.getAge());
        text_college.setText(PI.getCollege());
        text_city.setText(PI.getCity());
        text_about.setText(PI.getAbout());
        text_interest.setText(PI.getInterest());
        text_personality.setText(PI.getPersonality());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(PI.getGraph()));
            Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            ImageButton.setImageBitmap(imageBitmap);
            //ImageButton.setImageURI();
        }
        catch(Exception e){
            Toast.makeText(FriendInfoActivity.this, "no image", Toast.LENGTH_SHORT).show();
        }
    }
}
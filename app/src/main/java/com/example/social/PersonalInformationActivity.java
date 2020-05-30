package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class PersonalInformationActivity extends AppCompatActivity {
    ImageButton ImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ImageButton = (ImageButton)findViewById(R.id.image);
    }

    public void AddPhoto(View view) {
        //Intent next_page = new Intent(PersonalInformationActivity.this , AddPhoto.class );
        //startActivity(next_page);
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100){
            Uri imageUri = data.getData();
            ImageButton.setImageURI(imageUri);
        }
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
        Intent next_page = new Intent(PersonalInformationActivity.this , SwipeActivity.class );
        startActivity(next_page);
    }

}

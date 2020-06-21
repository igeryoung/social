package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.social.Image.CircleTransform;
import com.example.social.database.AccountDB;
import com.example.social.database.ImageDB;
import com.example.social.database.PersonalInformationDB;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PersonalInformationActivity extends AppCompatActivity {
    private String account;
    private AccountDB mAccountDB;
    private PersonalInformationDB mPInformationDB;
    private ImageDB mImageDB;
    private PersonalInformation mPI;

    ImageButton ImageButton;
    private Photo addphoto; // Image storage


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        mPI = (PersonalInformation) intent.getSerializableExtra("mPI");
        if(mPI == null) Log.d("PIActivity", "got null");
        else Log.d("PIActivity", mPI.toString());

        this.mAccountDB = new AccountDB();
        this.mImageDB = new ImageDB(account);
        this.mPInformationDB = new PersonalInformationDB();

        ImageButton = findViewById(R.id.image);
        addphoto = new Photo();
        ImageButton.setTag(addphoto.target);

        if(mPI != null)
            show_last_change();
    }

    // restore user data from last change
    private void show_last_change() {

        EditText text_name = findViewById(R.id.name);
        EditText text_gender = findViewById(R.id.gender);
        EditText text_age = findViewById(R.id.age);
        EditText text_college = findViewById(R.id.college);
        EditText text_city = findViewById(R.id.city);
        EditText text_about = findViewById(R.id.about);
        EditText text_interest = findViewById(R.id.interest);
        EditText text_personality = findViewById(R.id.personality);
        // " " put info of personalInfo get by id


        text_name.setText(mPI.getName());
        text_gender.setText(mPI.getGender());
        text_age.setText(mPI.getAge());
        text_college.setText(mPI.getCollege());
        text_city.setText(mPI.getCity());
        text_about.setText(mPI.getAbout());
        text_interest.setText(mPI.getInterest());
        text_personality.setText(mPI.getInterest());
        //Toast.makeText(PersonalInformationActivity.this, mPI.getGraph(), Toast.LENGTH_SHORT).show();

        //load url into ImageButton
        Picasso.get().load(mPI.getGraph()).transform(new CircleTransform()).into(ImageButton);
        addphoto.setBitmapByURL(mPI.getGraph());
    }
    //open gallery and add selfie
    public void AddPhoto(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);
    }
    //get data after choosing image from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 100){
            Picasso.get().load(data.getData()).transform(new CircleTransform()).into(ImageButton);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                addphoto.setBitmap(bitmap);
                addphoto.setUri(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
        }
    }


    public void Certain(View view) {
        try{
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
            EditText text_interest = findViewById(R.id.interest);
            String interest = text_interest.getText().toString();
            EditText text_personality = findViewById(R.id.personality);
            String personality = text_personality.getText().toString();


            if(!addphoto.ImageSetOrNot()){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.image_blank);
            }else if(text_name.getText().length() == 0){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.name_blank);
            }else if(text_gender.getText().length() == 0){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.gender_blank);
            }else if(text_age.getText().length() == 0){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.age_blank);
            }else if(text_college.getText().length() == 0){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.college_blank);
            }else if(text_city.getText().length() == 0){
                throw new PersonalInformationException(PersonalInformationException.ErrorType.city_blank);
            }else if(text_about.getText().length() == 0) {
                throw new PersonalInformationException(PersonalInformationException.ErrorType.about_blank);
            }else if(text_personality.getText().length() == 0) {
                throw new PersonalInformationException(PersonalInformationException.ErrorType.personality_blank);
            }else if(text_interest.getText().length() == 0) {
                throw new PersonalInformationException(PersonalInformationException.ErrorType.interest_blank);
            }

            PersonalInformation PI = new PersonalInformation(account, name, addphoto.getImageUri().toString(), about, college, city, age, gender, interest, personality);
            mPInformationDB.insertPI(PI);
            if(addphoto.ImageSetOrNot())
                mImageDB.updateURL(addphoto.getImageBitmap());
            Toast.makeText(PersonalInformationActivity.this, "個人資料新增成功", Toast.LENGTH_SHORT).show();

            startSwipe();

        }catch (PersonalInformationException e){
            Toast.makeText(PersonalInformationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish(){
        startSwipe();
    }

    public void Cancel(View view) {
        finish();
    }

    public void startSwipe(){
//        Intent next_page = new Intent(PersonalInformationActivity.this , SwipeActivity.class );
//        next_page.putExtra("account" , account);
//        startActivity(next_page);
            mPInformationDB.getOtherPIInMain(PersonalInformationActivity.this, 20, account);
    }

}

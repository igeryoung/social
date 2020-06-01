package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.social.database.FireBaseDB;
import com.example.social.database.ImageDB;

public class PersonalInformationActivity extends AppCompatActivity {
    private String account;
    private FireBaseDB mDataBase;
    private ImageDB mImageDB;
    ImageButton ImageButton;
    private Uri imageUri;
    boolean ImageSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        this.mDataBase = new FireBaseDB();
        this.mImageDB = new ImageDB(account);

        ImageButton = (ImageButton)findViewById(R.id.image);
        ImageSet = false;
    }

    public void AddPhoto(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100){
            this.imageUri = data.getData();
            ImageButton.setImageURI(imageUri);
            ImageSet = true;
            System.out.println(imageUri);
        }
        else {
            //這次選取有沒有照片
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
        }
        ImageSetOrNot();
    }

    public boolean ImageSetOrNot(){
        //現在有沒有存照片
        return ImageSet;
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

            if(!ImageSetOrNot()){
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
            }
            //System.out.println(imageUri.);
            String imageDownloadURL = mImageDB.getURL(imageUri);
            System.out.println("get imageURL => " + imageDownloadURL);

            PersonalInformation PI = new PersonalInformation(account, name, imageDownloadURL, about, college, city, age, gender, "", "");

            mDataBase.insertPI(PI);
            Toast.makeText(PersonalInformationActivity.this, "個人資料新增成功", Toast.LENGTH_SHORT).show();

            startSwipe();

        }catch (PersonalInformationException e){
            Toast.makeText(PersonalInformationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view) {
        startSwipe();
    }

    public void startSwipe(){
        Intent next_page = new Intent(PersonalInformationActivity.this , SwipeActivity.class );
        next_page.putExtra("account" , account);
        startActivity(next_page);
    }

}

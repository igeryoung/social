package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.social.database.AccountDB;
import com.example.social.database.ImageDB;
import com.example.social.database.PersonalInformationDB;

public class PersonalInformationActivity extends AppCompatActivity {
    private String account;
    private AccountDB mAccountDB;
    private PersonalInformationDB mPInformationDB;
    private ImageDB mImageDB;
    private PersonalInformation mPI;

    ImageButton ImageButton;

    boolean ImageSet;
    Uri imageUri;
    Bitmap imageBitmap;
    String absolutePath;

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

        ImageButton = (ImageButton)findViewById(R.id.image);
        ImageSet = false;
        if(mPI != null)
            show_last_change();
    }

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
        imageUri = Uri.parse(mPI.getGraph());
        try {
            //System.out.println(mPI.getGraph());
            Toast.makeText(PersonalInformationActivity.this, mPI.getGraph(), Toast.LENGTH_SHORT).show();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(mPI.getGraph()) );
            imageBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            ImageButton.setImageBitmap(imageBitmap);
            ImageSet = true;
            //ImageButton.setImageURI();
        }
        catch(Exception e){
            Toast.makeText(PersonalInformationActivity.this, "no image", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddPhoto(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 100){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                ImageButton.setImageBitmap(imageBitmap);
                //ImageButton.setImageURI(imageUri);
                ImageSet = true;
            } catch (Exception e){
                Toast.makeText(PersonalInformationActivity.this, "Empty file source", Toast.LENGTH_SHORT).show();
            }
            Context c = this.getApplicationContext();
            absolutePath = getFilePath_below19(c, imageUri);
            //Toast.makeText(PersonalInformationActivity.this, absolutePath, Toast.LENGTH_SHORT).show();

            System.out.println("absolute path => " + absolutePath);
        }
        else {
            //這次選取有沒有照片
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
        }
    }

    //現在有沒有存照片
    public boolean ImageSetOrNot(){
        return ImageSet;
    }
    //回傳絕對路徑
    public String getAbsolutePath(){
        if (ImageSetOrNot() == false){
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
            return null;
        }
        return absolutePath;
    }
    //回傳圖片Bitmap
    public Bitmap getImageBitmap(){
        if (ImageSetOrNot() == false){
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
            return null;
        }
        return imageBitmap;
    }
    //回傳圖片Uri
    public Uri getImageUri() {
        if (ImageSetOrNot() == false){
            Toast.makeText(PersonalInformationActivity.this, "未選取照片", Toast.LENGTH_SHORT).show();
            return null;
        }
        return imageUri;
    }

    // reference from https://blog.csdn.net/smileiam/article/details/79753745
    public  static String getFilePath_below19(Context context, Uri uri) {
        Cursor cursor = null ;
        String path = "" ;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null , null , null );
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path =cursor.getString(column_index);
        } finally {
            if (cursor != null ) {
                cursor.close();
            }
        }
        return path;
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
            }else if(text_personality.getText().length() == 0) {
                throw new PersonalInformationException(PersonalInformationException.ErrorType.personality_blank);
            }else if(text_interest.getText().length() == 0) {
                throw new PersonalInformationException(PersonalInformationException.ErrorType.interest_blank);
            }
            //System.out.println(imageUri.);
            PersonalInformation PI = new PersonalInformation(account, name, imageUri.toString(), about, college, city, age, gender, interest, personality);
            mPInformationDB.insertPI(PI);
            if(ImageSetOrNot())
                mImageDB.updateURL(getImageBitmap());
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
        Intent next_page = new Intent(PersonalInformationActivity.this , SwipeActivity.class );
        next_page.putExtra("account" , account);
        startActivity(next_page);
    }

}

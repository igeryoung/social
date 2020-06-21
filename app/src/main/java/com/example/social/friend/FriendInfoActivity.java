package com.example.social.friend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.social.Image.CircleTransform;
import com.example.social.R;
import com.example.social.model.PersonalInformation;
import com.squareup.picasso.Picasso;
/*
    page for showing and changing information of user's friend
    information : id(account) , name , graph , about , interest , personality , city , college
 */
public class FriendInfoActivity extends AppCompatActivity {
    public PersonalInformation PI;
    public ImageView IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        //initial
        Intent intent = getIntent();
        String info = intent.getStringExtra("friend_info");
        PI = new PersonalInformation(info);
        IV = findViewById(R.id.image);

        //show the data of user's friend
        show();

    }

    //show the data of user's friend
    private void show() {
        //init
        TextView text_name = findViewById(R.id.name);
        TextView text_gender = findViewById(R.id.gender);
        TextView text_age = findViewById(R.id.age);
        TextView text_college = findViewById(R.id.college);
        TextView text_city = findViewById(R.id.city);
        TextView text_about = findViewById(R.id.about);
        TextView text_interest = findViewById(R.id.interest);
        TextView text_personality = findViewById(R.id.personality);

        //set text
        text_name.setText(PI.getName());
        text_gender.setText(PI.getGender());
        text_age.setText(PI.getAge());
        text_college.setText(PI.getCollege());
        text_city.setText(PI.getCity());
        text_about.setText(PI.getAbout());
        text_interest.setText(PI.getInterest());
        text_personality.setText(PI.getPersonality());
        Picasso.get().load(PI.getGraph()).transform(new CircleTransform()).into(IV);
    }

    //Button "返回" click event : back to FriendActivity ( friend list )
    public void Certain(View view) {
        finish();
    }
}
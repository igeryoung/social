package com.example.social.Image;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.social.model.PersonalInformation;
import com.example.social.R;
import com.example.social.database.PersonalInformationDB;
import com.example.social.database.RelationDB;
import com.example.social.login.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

/*
    home page of user. Provide photo swipe ( like & dislike ), a left hand side hidden menu, which show photo & name & some function
    item in menu :
        personal information
        friend list
        logout
 */
public class SwipeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private PersonalInformationDB mPInformationDB;
    private RelationDB mRelationDB;
    private AppBarConfiguration mAppBarConfiguration;
    private String account;
    private ArrayList<PersonalInformation> strangerList;
    private PersonalInformation mPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //initial database
        mPInformationDB = new PersonalInformationDB();
        mRelationDB = new RelationDB();

        //get account and initial  PersonalInformation
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        strangerList = (ArrayList<PersonalInformation>) intent.getSerializableExtra("strangerList");
        mPI = (PersonalInformation) intent.getSerializableExtra ("mPI");

        //System.out.println("SwipeActivity get username = " + account);
        System.out.println("SwipeActivity get mPI = " + mPI);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_person, R.id.nav_friend, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //method for passing data to fragment
    public ArrayList<PersonalInformation> getStrangerList(){
        return strangerList;
    }
    //method for passing data to fragment
    public PersonalInformation getmPI(){return mPI;}

    // menu item controller
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TextView name = findViewById(R.id.nameInMenu);
        name.setText(mPI.getName());
        ImageView photo = findViewById(R.id.imageViewInMenu);
        Picasso.get().load(mPI.getGraph()).resize(250 , 250).transform(new CircleTransform()).into(photo);
        return true;
    }

    // show the fragment by navController
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // set the click event on menu item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            //"個人資料"
            case R.id.nav_person: {
                mPInformationDB.getMyPI(SwipeActivity.this, account);
                break;
            }

            //"好友名單"
            case R.id.nav_friend: {
                mRelationDB.getFriendList(SwipeActivity.this, account);
                break;
            }

            //"登出"
            case R.id.nav_logout: {
                Intent page = new Intent(SwipeActivity.this , MainActivity.class);
                startActivity(page);
                break;
            }
        }

        //close menu after click menu item
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // a AlertDialog to reassure user want to logout or not
    @Override
    public void finish(){
        new AlertDialog.Builder(SwipeActivity.this)
                .setIcon(null)
                .setTitle("是否要登出?")
                .setPositiveButton("登出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                }).setNegativeButton("取消",null).create()
                .show();
    }
}
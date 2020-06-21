package com.example.social;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.social.database.PersonalInformationDB;
import com.example.social.database.RelationDB;
import com.google.android.material.navigation.NavigationView;

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

        mPInformationDB = new PersonalInformationDB();
        mRelationDB = new RelationDB();

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        strangerList = (ArrayList<PersonalInformation>) intent.getSerializableExtra("strangerList");
        mPI = (PersonalInformation) intent.getSerializableExtra ("mPI");

        //System.out.println("SwipeActivity get username = " + account);
        System.out.println("SwipeActivity get mPI = " + mPI);
        //System.out.println("SwipeActivity get strangerList = " + strangerList);

        //setNavigationViewListener();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_person, R.id.nav_friend, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.test, menu);
        TextView name = findViewById(R.id.nameInMenu);
        name.setText("我誰?");
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_person: {
                mPInformationDB.getMyPI(SwipeActivity.this, account);
                break;
            }
            case R.id.nav_friend: {
                mRelationDB.getFriendList(SwipeActivity.this, account);
                break;
            }
            case R.id.nav_logout: {
                Intent page = new Intent(SwipeActivity.this , MainActivity.class);
                startActivity(page);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.graphics.Color;

import com.example.social.database.AccountDB;

public class MainActivity extends AppCompatActivity {
    private AccountDB mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        //final LinearLayout background = (LinearLayout)findViewById(R.id.background);
        //background.setBackgroundColor(Color.parseColor("#B0E0E6"));
        this.mDataBase = new AccountDB();
    }

    public void LogIn(View view) {

        try {
            //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();
            EditText text_password = findViewById(R.id.password);
            String password = text_password.getText().toString();
            //failure handle
            if(text_account.getText().length() == 0){
                throw new LogInException(LogInException.ErrorType.account_blank);
            }else if (text_password.getText().length() == 0){
                throw new LogInException(LogInException.ErrorType.password_blank);
            }

            mDataBase.LogIn(MainActivity.this, account, password);

        } catch (LogInException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        }
    }

    public void register(View view) {
        Intent next_page = new Intent(MainActivity.this , RegisterActivity.class );
        startActivity(next_page);
    }
}

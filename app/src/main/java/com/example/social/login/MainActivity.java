package com.example.social.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.database.AccountDB;
import com.example.social.register.RegisterActivity;

/*
*   main page , handle login event
*/
public class MainActivity extends AppCompatActivity {
    private AccountDB mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initial database of account
        this.mDataBase = new AccountDB();
    }

    public void LogIn(View view) {

        try {
            //get data in EditText
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

            //check account in database , login , and change page
            mDataBase.LogIn(MainActivity.this, account, password);

        } catch (LogInException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
        }
    }

    //Button "點此註冊" click event : change page to RegisterActivity
    public void register(View view) {
        Intent next_page = new Intent(MainActivity.this , RegisterActivity.class );
        startActivity(next_page);
    }
}

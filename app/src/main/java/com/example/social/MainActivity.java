package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.social.database.FireBaseDB;

public class MainActivity extends AppCompatActivity {
    private FireBaseDB mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        this.mDataBase = new FireBaseDB();
    }

    public void LogIn(View view) {

        try {
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();
            EditText text_password = findViewById(R.id.password);
            String password = text_password.getText().toString();
            //failure handle
            if(text_account.getText().length() == 0){
                throw new LogInException(LogInException.ErrorType.account_blank);
            }else if (text_password.getText().length() == 0){
                throw new LogInException(LogInException.ErrorType.password_blank);
            }else if(mDataBase.checkIfAccountExist(account) == false){
                throw new LogInException(LogInException.ErrorType.account_undefine);
            }else if(mDataBase.checkPassword(account, password) == false){
                throw new LogInException(LogInException.ErrorType.password_error);
            }
            // successful login and jump to next page

            Intent next_page = new Intent(MainActivity.this , PersonalInformationActivity.class );
            startActivity(next_page);

        } catch (LogInException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        Intent next_page = new Intent(MainActivity.this , RegisterActivity.class );
        startActivity(next_page);
    }
}

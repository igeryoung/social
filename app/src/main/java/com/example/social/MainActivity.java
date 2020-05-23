package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LogIn(View view) {
        Intent next_page = new Intent(MainActivity.this , PersonalInformationActivity.class );
        startActivity(next_page);
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
            }else if(false/*account == unregister*/){
                throw new LogInException(LogInException.ErrorType.account_undefine);
            }else if(false /*password unmatch*/){
                throw new LogInException(LogInException.ErrorType.password_error);
            }
            // successful login and jump to next page




        } catch (LogInException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        Intent next_page = new Intent(MainActivity.this , RegisterActivity.class );
        startActivity(next_page);
    }
}

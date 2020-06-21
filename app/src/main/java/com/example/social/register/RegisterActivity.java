package com.example.social.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.database.AccountDB;

/*
    page for register event
 */
public class RegisterActivity extends AppCompatActivity {
    private AccountDB mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.mDataBase = new AccountDB();
    }

    //Button "檢查帳號" click event : search database to make sure this account had been registered or not
    public void Check(View view) {
        try {
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();

            //if no account input , throw to RegisterException
            if(text_account.getText().length() == 0) {
                throw new RegisterException(RegisterException.ErrorType.account_blank);
            }

            //check account
            mDataBase.checkIfAccountExist(RegisterActivity.this, account);
        }catch (RegisterException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //Button "確定註冊" click event : back to MainActivity page
    public void RegisterAndLogin(View view) {
        try {
            //get text from EditText
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();
            EditText text_password = findViewById(R.id.password);
            String password = text_password.getText().toString();
            EditText text_assure = findViewById(R.id.assure);
            String assure = text_assure.getText().toString();

            //blank case check
            if(text_account.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.account_blank);
            }else if (text_password.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.password_blank);
            }else if(text_assure.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.assure_blank);
            }

            //assured password error
            if(assure.compareTo(password) != 0){
                throw new RegisterException(RegisterException.ErrorType.assure_error);
            }

            // successful login and jump to next page
            mDataBase.register(RegisterActivity.this, account, password);
            Toast.makeText(RegisterActivity.this, "註冊成功 !", Toast.LENGTH_SHORT).show();
            finish();

        } catch (RegisterException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

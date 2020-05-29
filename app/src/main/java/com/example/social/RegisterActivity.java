package com.example.social;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.database.FireBaseDB;

public class RegisterActivity extends AppCompatActivity {
    private FireBaseDB mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.mDataBase = new FireBaseDB();
    }

    public void Check(View view) {
        try {
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();
            if(text_account.getText().length() == 0) {
                throw new RegisterException(RegisterException.ErrorType.account_blank);
            }
            DoCheck();
        }catch (RegisterException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void RegisterAndLogin(View view) {
        try {
            //get text
            EditText text_account = findViewById(R.id.account);
            String account = text_account.getText().toString();
            EditText text_password = findViewById(R.id.password);
            String password = text_password.getText().toString();
            EditText text_assure = findViewById(R.id.assure);
            String assure = text_assure.getText().toString();

            //blank handle
            if(text_account.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.account_blank);
            }else if (text_password.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.password_blank);
            }else if(text_assure.getText().length() == 0){
                throw new RegisterException(RegisterException.ErrorType.assure_blank);
            }

            //account exist
            if (DoCheck() == false){
                return;
            }

            //assure error
            if(assure.compareTo(password) != 0){
                throw new RegisterException(RegisterException.ErrorType.assure_error);
            }

            // successful login and jump to next page

            mDataBase.insertAccount(account, password);

            /*
            Intent next_page = new Intent(MainActivity.this , next.class );
            next_page.putExtra("account" , account);
            startActivityForResult(next_page , 0);
            */
        } catch (RegisterException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean DoCheck(){
        EditText text_account = findViewById(R.id.account);
        String account = text_account.getText().toString();
        TextView input_msg = findViewById(R.id.msg);
        if(mDataBase.checkIfAccountExist(account)){
            input_msg.setText("此帳號已被註冊");
            return false;
        }else{
            input_msg.setText("此帳號可使用");
            return true;
        }
    }
}

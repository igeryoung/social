package com.example.social.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.social.model.Account;
import com.example.social.login.MainActivity;
import com.example.social.personal_information.PersonalInformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/** Using Firestore as online database, and we make page changing within these functions since it's asynchronous*/

public class AccountDB {
    private static final String TAG = "accountMsg";
    private FirebaseFirestore db;

    public AccountDB(){
        this.db = FirebaseFirestore.getInstance();
    }

    /** Check mUsername and mPassword whether exists or correct*/
    public void LogIn(final Context context, String mUsername, final String mPassword){
        db.collection("account")
                .document(mUsername)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Account account = document.toObject(Account.class);
                                if(account.getPassword().compareTo(mPassword) == 0){
                                    if(account.getHavePI() == false){
                                        Log.d(TAG, "Correct password, go create PI!");
                                        Intent next_page = new Intent(context , PersonalInformationActivity.class );
                                        next_page.putExtra( "account", account.getAccount());
                                        context.startActivity(next_page);
                                    }
                                    else{
                                        Log.d(TAG, "Correct password, already have PI!");
                                        PersonalInformationDB mPInformationDB = new PersonalInformationDB();
                                        mPInformationDB.getOtherPIInMain(context, 20, account.getAccount());
                                    }
                                }
                                else{
                                    Log.d(TAG, "Wrong password!");
                                    Toast.makeText(context, "密碼錯誤請再試一次\n", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Log.d(TAG, "No existing account!");
                                Toast.makeText(context, "查無此帳號\n", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                            Toast.makeText(context, "讀取失敗\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /** Toast message showing whether there's existed account already*/
    public void checkIfAccountExist(final Context context, String mUserName){
        db.collection("account")
                .document(mUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.d(TAG, "username already exists!");
                                Toast.makeText(context, "此帳號已被註冊\n", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.d(TAG, "valid username!");
                                Toast.makeText(context, "此帳號可使用\n", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                            Toast.makeText(context, "讀取失敗\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /** If successfully register, turn back to mainActivity; otherwise, toast error massage according to different condition*/
    public void register(final Context context, final String mUserName, final String mPassword){
        db.collection("account")
                .document(mUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.d(TAG, "username already exists");
                                Toast.makeText(context, "此帳號已被註冊\n", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.d(TAG, "insert a new account!");
                                insertAccount(mUserName, mPassword);
                                Intent next_page = new Intent(context , MainActivity.class );
                                context.startActivity(next_page);
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                            Toast.makeText(context, "讀取失敗\n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /** Insert an LEGAL account with password*/
    public void insertAccount(String mUserName, String mPassword){
        Account account = new Account(mUserName, mPassword, false);
        db.collection("account").document(mUserName).set(account);
    }

    /** Change password*/
    public void changePassword(final String mUserName , String mPassword){
        db.collection("account")
                .document(mUserName)
                .update("password", mPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, mUserName + " successfully change password");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, mUserName + " failed to change password");
                    }
                });
    }

}

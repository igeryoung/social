package com.example.social.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.social.Account;
import com.example.social.MainActivity;
import com.example.social.PersonalInformation;
import com.example.social.PersonalInformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class FireBaseDB {
    private static final String TAG = "accountMsg";
    private FirebaseFirestore db;
    private boolean checkRet;

    public FireBaseDB(){
        this.db = FirebaseFirestore.getInstance();
    }

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
                                    Intent next_page = new Intent(context , PersonalInformationActivity.class );
                                    context.startActivity(next_page);
                                }
                                else{
                                    Toast.makeText(context, "密碼錯誤請再試一次\n", Toast.LENGTH_SHORT).show();
                                }
                                Log.d(TAG, "Document exists!");
                            }
                            else{
                                Toast.makeText(context, "查無此帳號\n", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Document does not exist!");
                            }
                        } else {
                            Toast.makeText(context, "讀取失敗\n", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });
    }

    public boolean checkIfAccountExist(String mUserName){
        db.collection("account")
                .document(mUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                checkRet = true;
                                Log.d(TAG, "Document exists!");
                            }
                            else{
                                checkRet = false;
                                Log.d(TAG, "Document does not exist!");
                            }
                        } else {
                            checkRet = false;
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });
        return checkRet;
    }

    public boolean checkPassword(String mUserName, final String mPassword){
        db.collection("account")
                .document(mUserName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Account account = documentSnapshot.toObject(Account.class);
                        if (account.getPassword().compareTo(mPassword) == 0) {
                            checkRet = true;
                            Log.d(TAG, "correct password");
                        } else {
                            checkRet = false;
                            Log.d(TAG, "wrong password");
                        }
                    }
                });
        return checkRet;
    }

    public void insertAccount(String mUserName, String mPassword){
        Account account = new Account(mUserName, mPassword);
        db.collection("account").document(mUserName).set(account);
    }

    public boolean changePassword(final String mUserName , String mPassword){
        db.collection("account")
                .document(mUserName)
                .update("password", mPassword)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        checkRet = true;
                        Log.d(TAG, mUserName + " successfully change password");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        checkRet = false;
                        Log.d(TAG, mUserName + " failed to change password");
                    }
                });
        return checkRet;
    }

    /** PersonalInformation.id == userName ? */
    public void insertPI(PersonalInformation PI){
        db.collection("account").document(PI.getId()).set(PI);
    }
}

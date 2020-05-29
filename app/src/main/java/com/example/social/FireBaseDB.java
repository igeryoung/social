package com.example.social;

import android.util.Log;

import androidx.annotation.NonNull;

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
                        if(account.getPassword().compareTo(mPassword) == 0)
                            checkRet = true;
                        else
                            checkRet = false;
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

}

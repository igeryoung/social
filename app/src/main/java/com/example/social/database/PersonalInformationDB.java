package com.example.social.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.social.PersonalInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class PersonalInformationDB {
    private static final String TAG = "PersonalInformationMsg";
    private FirebaseFirestore db;

    public PersonalInformationDB(){
        this.db = FirebaseFirestore.getInstance();
    }

    /** PersonalInformation.id == userName ? */
    public void insertPI(PersonalInformation PI){
        db.collection("personalInformation").document(PI.getId()).set(PI);
        db.collection("account")
                .document(PI.getId())
                .update("havePI", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "successfully insert personalInformation");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "fail to insert personalInformation");
                    }
                });
    }

    public void updateGraph(String URL, String mUsername){
        db.collection("personalInformation").document(mUsername)
                .update("graph", URL)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "successfully update graph URL");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "fail to update graph URL");
                    }
                });
    }
}

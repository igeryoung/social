package com.example.social.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.social.FriendActivity;
import com.example.social.PersonalInformation;
import com.example.social.PersonalInformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

    public void getMyPI(final Context context, final String mUsername){
        db.collection("personalInformation")
                .document(mUsername)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                PersonalInformation mPI = document.toObject(PersonalInformation.class);
                                Log.d(TAG, "get personalInformation => " + mPI.toString());
                                Log.d(TAG, "get personalInformation => " + document.getData());
                                Intent personInfo = new Intent(context , PersonalInformationActivity.class);
                                personInfo.putExtra("account" , mUsername);
                                personInfo.putExtra("mPI", mPI);
                                context.startActivity(personInfo);
                            }
                            else{
                                Log.d(TAG, "No such document");
                            }
                        }
                        else{
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void getOtherPI(String targetField, String targetValue, int number){
        db.collection("personalInformation")
                .whereEqualTo(targetField, targetValue)
                .limit(number)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<PersonalInformation> PIlist = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                PIlist.add(doc.toObject(PersonalInformation.class));
                                System.out.println(doc.toObject(PersonalInformation.class).toString());
                            }
                        }
                        Log.d(TAG, "successfully listen");
                    }
                });
    }

    public void getOtherPI(final Context context, int number, final String mUsername){
        db.collection("personalInformation")
                .limit(number)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<PersonalInformation> strangerList = new ArrayList<PersonalInformation>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                strangerList.add(document.toObject(PersonalInformation.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            Intent swipe_page = new Intent(context , FriendActivity.class);
                            swipe_page.putExtra("account" , mUsername);
                            swipe_page.putExtra("strangerList", strangerList);
                            context.startActivity(swipe_page);
                        } else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

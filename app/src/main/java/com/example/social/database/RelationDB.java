package com.example.social.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.social.FriendActivity;
import com.example.social.PersonalInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RelationDB {
    private static final String TAG = "FriendMsg";
    private FirebaseFirestore db;

    public RelationDB(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void addLike(final String mUsername, final String otherUsername){
        db.collection("relation")
                .document(mUsername)
                .collection("like")
                .document(otherUsername)
                .update("like", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, mUsername + " add like for " + otherUsername);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, mUsername + " fail to add like for " + otherUsername);
                    }
                });
        db.collection("relation")
                .document(otherUsername)
                .collection("like")
                .document(mUsername)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                if(document.getBoolean("like") == true){
                                    addFriend(mUsername, otherUsername);
                                    addFriend(otherUsername, mUsername);
                                } else {
                                    Log.d(TAG, otherUsername + " doesn't like " + mUsername);
                                }
                            } else {
                                Log.d(TAG, otherUsername + " hasn't read " + mUsername + "PI");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void addDislike(final String mUsername, final String otherUsername){
        db.collection("relation")
                .document(mUsername)
                .collection("like")
                .document(otherUsername)
                .update("like", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, mUsername + " add like for " + otherUsername);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, mUsername + " fail to add like for " + otherUsername);
                    }
                });
    }

    public void addFriend(final String aUsername, final String bUsername){
        db.collection("personalInformation")
                .document(aUsername)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                PersonalInformation mPI = document.toObject(PersonalInformation.class);
                                assert mPI != null;
                                db.collection("relation")
                                        .document(bUsername)
                                        .collection("friend")
                                        .document(aUsername)
                                        .set(mPI)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, bUsername +" successfully add " + aUsername + " as friend");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, bUsername +" fail to add " + aUsername + " as friend");
                                            }
                                        });
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


    public void getFriendList(final Context context, final String mUsername){
        db.collection("relation")
                .document(mUsername)
                .collection("friend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<PersonalInformation> friendList = new ArrayList<PersonalInformation>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                friendList.add(document.toObject(PersonalInformation.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            Intent friend_page = new Intent(context , FriendActivity.class);
                            friend_page.putExtra("account" , mUsername);
                            friend_page.putExtra("friendList", friendList);
                            context.startActivity(friend_page);
                        } else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void updateStranger(){
        //TODO
    }
}
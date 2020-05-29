package com.example.social.database;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageDB {
    private static final String TAG = "imageMsg";
    private StorageReference mStorageRef;
    private String mUserName;
    private boolean checkRet;

    public ImageDB(String mUserName){
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.mUserName = mUserName;
    }

    public String getURL(Uri file){
        final StorageReference mRef = mStorageRef.child(mUserName).child("thumbnail.jpg");

        mRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "successfully upload");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(TAG, "upload failed");
                    }
                });

        return mRef.getDownloadUrl().toString();
    }
}

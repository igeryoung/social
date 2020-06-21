package com.example.social.database;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/** Using FirebaseStorage as image database*/
public class ImageDB {
    private static final String TAG = "imageMsg";
    private StorageReference mStorageRef;
    private String mUserName;

    public ImageDB(String mUserName){
        mStorageRef = FirebaseStorage.getInstance().getReference();
        this.mUserName = mUserName;
    }

    /**upload the image to image database and update the corresponding URL in the personalInformation database*/
    public void updateURL(Bitmap bitmap){
        final StorageReference mRef = mStorageRef.child(mUserName).child("thumbnail.jpg");
        //Uri file = Uri.fromFile(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        mRef.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mRef.getDownloadUrl().
                                addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        PersonalInformationDB mPInformationDB = new PersonalInformationDB();
                                        mPInformationDB.updateGraph(uri.toString(), mUserName);
                                        // Got the download URL for 'users/me/profile.png'
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        Log.d(TAG, "successfully upload");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d(TAG, "upload failed");
                    }
                });
    }
}

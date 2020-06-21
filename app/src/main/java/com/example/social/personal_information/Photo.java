package com.example.social.personal_information;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

// stores photo attribute
public class Photo extends AppCompatActivity {
    boolean ImageSet;
    Uri imageUri;
    Bitmap imageBitmap;
    Target target;


    public Photo(){
        ImageSet = false;
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageBitmap = bitmap;
                if(imageBitmap == null)
                    System.out.println("Set Bitmap failed");
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                System.out.println("Set Bitmap failed");
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    public void setBitmap(Bitmap bitmap) {
        imageBitmap = bitmap;
        ImageSet = true;
    }


    //現在有沒有存照片
    public boolean ImageSetOrNot(){
        return ImageSet;
    }
    //回傳圖片Bitmap
    public Bitmap getImageBitmap(){
        if (ImageSetOrNot() == false){
            return null;
        }
        return imageBitmap;
    }
    //回傳圖片Uri
    public Uri getImageUri() {
        if (ImageSetOrNot() == false){
            return null;
        }
        return imageUri;
    }

    // load bitmap from URL
    public void setBitmapByURL(String image_url) {
        imageUri = Uri.parse(image_url);
        Picasso.get().load(image_url).into(target);
        ImageSet = true;
    }

    public void setUri(Uri data) {
        imageUri = data;
    }
}
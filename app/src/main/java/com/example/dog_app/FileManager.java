package com.example.dog_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {

    private FileManager() { }

    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
    }

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_IMAGE_GALLERY =2;


    //permissions
    public static final int REQUEST_PERMISSIONS = 3;

    public static void dispatchTakePictureIntent(Activity context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                context.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
            }else {
                Intent takePictureIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    context.startActivityForResult(takePictureIntent,
                            REQUEST_IMAGE_CAPTURE);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            Intent takePictureIntent = new
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                context.startActivityForResult(takePictureIntent,
                        REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String byteArrayToString(byte[] bitmapBytes) {
        Gson g = new Gson();
        String stringForm = g.toJson(bitmapBytes);
        return stringForm;
    }

    public static byte[] stringToByteArray(String bytesString) {
        Gson g = new Gson();
        byte[] bytes = g.fromJson(bytesString,byte[].class);
        return bytes;
    }

    public static void dispatchGalleryIntent(Activity context) {
        Intent galleryIntent = new
                Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        try {
            context.startActivityForResult(galleryIntent,
                    REQUEST_IMAGE_GALLERY);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] bitmapToBytes(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap bitmapFromBytes(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bmp;
    }


}

package com.example.dog_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dog_app.models.Dog;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;

public class AddDogActivity extends AppCompatActivity  {

    ImageView dogImageView;
    EditText dogNameEt,dogBreedEt,dogAgeEt,dogPriceEt;
    RadioGroup dogVaccineRg,dogGenderRg;
    Button addButtonSubmit;

    String dogImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        dogGenderRg = findViewById(R.id.dog_gender_rg);
        dogImageView = findViewById(R.id.add_dog_image);
        dogNameEt = findViewById(R.id.dog_name_et);
        dogBreedEt = findViewById(R.id.dog_breed_et);
        dogAgeEt = findViewById(R.id.dog_age_et);
        dogPriceEt = findViewById(R.id.dog_price_et);
        dogVaccineRg = findViewById(R.id.dog_vaccine_rg);
        addButtonSubmit = findViewById(R.id.add_button_submit);
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();

        dogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaActionDialog();
            }
        });

        addButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidFields()) {
                    Dog dog = new Dog(dogNameEt.getText().toString(),dogImage,
                            dogBreedEt.getText().toString(),
                            getSelectedDogGender(),
                            Integer.parseInt(dogAgeEt.getText().toString()),
                            Double.parseDouble(dogPriceEt.getText().toString()),
                            getSelectedVaccineOption());
                    LocalStore.getInstance(AddDogActivity.this)
                            .addDogToStorage(AddDogActivity.this,dog);

                    Snackbar.make(AddDogActivity.this,findViewById(R.id.addDog_layout),"Successfully added dog " + dog.getName(),Snackbar.LENGTH_SHORT).show();


                    finish();
                }else {
                    Snackbar.make(AddDogActivity.this,findViewById(R.id.addDog_layout),"There was a problem adding the dog please make sure you fill all the fields and pick an image",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getSelectedDogGender() {
        RadioButton maleButton = findViewById(R.id.male_rb);
        int selected = dogGenderRg.getCheckedRadioButtonId();
        return selected == maleButton.getId() ? "Male" : "Female";
    }

    private boolean getSelectedVaccineOption() {
        RadioButton isVaccinated = findViewById(R.id.vaccinated_rb);
        int selected = dogVaccineRg.getCheckedRadioButtonId();
        return selected == isVaccinated.getId();
    }

    private boolean isValidFields() {
        if (dogNameEt.getText().toString().isEmpty()) {
            return false;
        }
        if(dogAgeEt.getText().toString().isEmpty()) {
            return false;
        }
        if(dogBreedEt.getText().toString().isEmpty()) {
            return false;
        }
        if(dogPriceEt.getText().toString().isEmpty()) {
            return false;
        }
        if(dogImage == null) {
            return false;
        }
        return true;
    }

    private void mediaActionDialog() {
        View popup = LayoutInflater.from(this).inflate(R.layout.gallery_camera_popup,null,false);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(popup)
                .setTitle("Dog App")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioButton gallery = popup.findViewById(R.id.gallery_btn);
                        int selected = ((RadioGroup)popup.findViewById(R.id.media_selection)).getCheckedRadioButtonId();
                        if(selected == gallery.getId()) {
                            FileManager.dispatchGalleryIntent(AddDogActivity.this);
                        }else {
                            FileManager.dispatchTakePictureIntent(AddDogActivity.this);
                        }
                    }
                })
                .setNegativeButton("Cancel",null).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            if(requestCode == FileManager.REQUEST_IMAGE_CAPTURE) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    dogImageView.setImageBitmap(imageBitmap);
                    byte[] imageBytes = FileManager.bitmapToBytes(imageBitmap);
                    this.dogImage = FileManager.byteArrayToString(imageBytes);
            }else if(requestCode == FileManager.REQUEST_IMAGE_GALLERY) {
                Uri uri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    dogImageView.setImageBitmap(imageBitmap);
                    byte[] imageBytes = FileManager.bitmapToBytes(imageBitmap);
                    this.dogImage = FileManager.byteArrayToString(imageBytes);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            Toast.makeText(this,"There was a problem selecting image",Toast.LENGTH_SHORT).show();
        }
    }
}

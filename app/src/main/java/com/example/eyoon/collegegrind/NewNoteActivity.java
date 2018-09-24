package com.example.eyoon.collegegrind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    // Stores the most recent filepath for saved image
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Intent sourceIntent = getIntent();

        mCurrentPhotoPath = sourceIntent.getStringExtra(MainActivity.FILE_PATH_KEY);
        ImageView mImageView = (ImageView) findViewById(R.id.result_image_view);
        ImageEngine.setImageView(mImageView, mCurrentPhotoPath);
    }
}
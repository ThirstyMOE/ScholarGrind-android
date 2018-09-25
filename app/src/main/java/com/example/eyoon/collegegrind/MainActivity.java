package com.example.eyoon.collegegrind;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Request code for taking an image
    public static final int REQUEST_TAKE_PHOTO = 1;
    // File Path Intent Bundle Key
    public static final String FILE_PATH_KEY = "com.example.eyoon.collegegrind.MainActivity.FilePath";
    // Stores the most recent filepath for saved image
    private static String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickOpenButton(View view)
    {
        Intent openActivityIntent = new Intent(this, OpenNoteActivity.class);
        startActivity(openActivityIntent);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void OnClickNewButton(View view)
    {
        dispatchTakePictureIntent();
    }


    /**
     * An event handler for taking a picture. Will take full size image and save it.
     * https://developer.android.com/training/camera/photobasics
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("FROM ANDROID STUDIO", "Could not Create Image File Placeholder");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            } else {
                Log.e("FROM ANDROID STUDIO", "THIS PHOTOFILE IS NULL");
            }
        }
    }

    /**
     * Creates a collision resistant file path that will attach to an image.
     *
     * @return A file object with a file name based on the current time
     * @throws IOException Will throw if file creation messes up along the way. Most likely due to a getExternalFilesDir() call.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Sets the imageview on the screen to show a bitmap of the most recent image taken.
     *
     * @param requestCode The Request Code
     * @param resultCode The Result Code
     * @param data The intent data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if(resultCode != RESULT_OK)
            {
                // There is no picture inside the file, delete the empty file
                File currentPhotoFile = new File(mCurrentPhotoPath);
                // Deletion code Accessed at https://stackoverflow.com/questions/3554722/how-to-delete-internal-storage-file-in-android
                // on 9-25-2018
                boolean deleted = currentPhotoFile.delete();
                Log.e("DEBUGTEST", "DELETED " + mCurrentPhotoPath);
            }
            else
            {
                Intent recentImageIntent = new Intent(this, MainActivity.class);
                recentImageIntent.putExtra(FILE_PATH_KEY, mCurrentPhotoPath);

                startActivity(recentImageIntent);
                Log.e("DEBUGTEST", "NO DELETION");
            }
        }
    }
}

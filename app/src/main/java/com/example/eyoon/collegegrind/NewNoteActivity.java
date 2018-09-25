//package com.example.eyoon.collegegrind;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.icu.text.SimpleDateFormat;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import java.io.Console;
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//
//public class NewNoteActivity extends AppCompatActivity {
//
//    // Stores the most recent filepath for saved image
//    private String mCurrentPhotoPath;
//
//    // Request code for taking an image
//    public static final int REQUEST_TAKE_PHOTO = 2;
//    // File Path Intent Bundle Key
//    public static final String FILE_PATH_KEY = "com.example.eyoon.collegegrind.NewNoteActivity.FilePath";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_note);
//        Intent sourceIntent = getIntent();
//
//        mCurrentPhotoPath = sourceIntent.getStringExtra(MainActivity.FILE_PATH_KEY);
//
//        File photoFile = new File(mCurrentPhotoPath);
//        Uri data = FileProvider.getUriForFile(this,
//                "com.example.android.fileprovider",
//                photoFile);
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, data);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(intent);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void onClickNewPhotoButton(View view)
//    {
//        dispatchTakePictureIntent();
//    }
//    /**
//     * An event handler for taking a picture. Will take full size image and save it.
//     * https://developer.android.com/training/camera/photobasics
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.e("FROM ANDROID STUDIO", "Could not Create Image File Placeholder");
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            } else {
//                Log.e("FROM ANDROID STUDIO", "THIS PHOTOFILE IS NULL");
//            }
//        }
//    }
//
//    /**
//     * Creates a collision resistant file path that will attach to an image.
//     *
//     * @return A file object with a file name based on the current time
//     * @throws IOException Will throw if file creation messes up along the way. Most likely due to a getExternalFilesDir() call.
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    /**
//     * Sets the imageview on the screen to show a bitmap of the most recent image taken.
//     *
//     * @param requestCode The Request Code
//     * @param resultCode The Result Code
//     * @param data The intent data
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Intent recentImageIntent = new Intent(this, NewNoteActivity.class);
//            recentImageIntent.putExtra(FILE_PATH_KEY, mCurrentPhotoPath);
//            startActivity(recentImageIntent);
//
//        }
//    }
//}
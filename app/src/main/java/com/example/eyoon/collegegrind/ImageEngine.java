package com.example.eyoon.collegegrind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.ImageView;

import java.io.IOException;

/**
 * A class full of static methods designed for setting content of ImageViews
 */
public class ImageEngine {
    private final static int DEFAULT_DIMENSION = 300;
    public static void setImageView(ImageView mImageView, String mImageFilePath)
    {
        setImageView(mImageView, mImageFilePath, false, DEFAULT_DIMENSION);
    }
    public static void setImageView(ImageView mImageView, String mImageFilePath, int targetDimension)
    {
        setImageView(mImageView, mImageFilePath, true, targetDimension);
    }
    /**
     * Method for resizing Bitmaps Accessed from
     * https://developer.android.com/training/camera/photobasics
     * on 7/9/2018
     *
     * All methods for reorienting Bitmap Accessed from
     * https://stackoverflow.com/questions/20478765/how-to-get-the-correct-orientation-of-the-image-selected-from-the-default-image
     * on 7/9/2018
     */
    private static void setImageView(ImageView mImageView, String mImageFilePath, boolean scalingImage, int targetDimension)
    {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFilePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        if(scalingImage)
        {
            // Get the dimensions of the View
            int targetW = targetDimension;
            int targetH = targetDimension;
            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            bmOptions.inSampleSize = scaleFactor;
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mImageFilePath, bmOptions);
        bitmap = fixOrientation(bitmap, mImageFilePath);
        mImageView.setImageBitmap(bitmap);
    }
    private static Bitmap fixOrientation(Bitmap bitmap, String mImageFilePath)
    {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mImageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        return rotateBitmap(bitmap, orientation);
    }
    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}

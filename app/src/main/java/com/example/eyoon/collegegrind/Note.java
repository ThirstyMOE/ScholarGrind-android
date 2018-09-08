package com.example.eyoon.collegegrind;

import android.graphics.Bitmap;

public class Note {
    private static final String DEFAULT_FILE_NAME = "<No Name>";

    private String fileName;
    private String filePath;
    private Bitmap thumbNail;

    public Note(String pFilePath)
    {
        this(DEFAULT_FILE_NAME, pFilePath);
    }
    public Note(String pFileName, String pFilePath)
    {
        fileName = pFileName;
        filePath = pFilePath;
    }

    public String getFileName()
    {
        return fileName;
    }
    public String getFilePath()
    {
        return filePath;
    }

    @Override
    public String toString()
    {
        return getFilePath();
    }

    private void loadFileInformation()
    {
        
    }
}

package com.example.eyoon.collegegrind;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accessed AdapterView methods from https://developer.android.com/guide/topics/ui/declaring-layout#AdapterViews
 * on 7/14/2018
 */
public class OpenNoteActivity extends AppCompatActivity {

    private ListView notesListView;
    private Note[] notesCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);

        getReferenceNotesListView();
        retrieveNotesFromFileSystem();
        populateNotesListView();
    }

    /**
     * Gets files from the local storage on the device, and overwrites into the notesCollection.
     * Does not return anything.
     */
    private void retrieveNotesFromFileSystem()
    {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        List<File> files = getListFiles(storageDir);
        notesCollection = new Note[files.size()];
        for(int i = 0; i < notesCollection.length; i++)
        {
            File file = files.get(i);
            String name = file.getName();
            String path = file.getAbsolutePath();
            Note note = new Note(name, path);
            notesCollection[i] = note;
        }
    }

    /**
     * https://stackoverflow.com/questions/9530921/list-all-the-files-from-all-the-folder-in-a-single-list
     * @param parentDir The directory where all the pictures are stored.
     * @return List of all files from the selected parent directory
     */
    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".jpg")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    /**
     * A method that fills all the entries in the notes_list_view based on the files it finds in the storage system
     */
    private void populateNotesListView()
    {
        ArrayAdapter<Note> notesAdapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, notesCollection);

        notesListView.setAdapter(notesAdapter);

        attachItemClickListener(notesListView);
    }
    private void attachItemClickListener(AdapterView view)
    {
        // Create a message handling object as an anonymous class.
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click]
                Note clickedNote = notesCollection[position];
                openUpImageViewer(clickedNote.getFilePath());
            }
        };
        view.setOnItemClickListener(mMessageClickedHandler);
    }

    /**
     * Gets a object reference to the notes_list_view
     */
    private void getReferenceNotesListView()
    {
        notesListView = findViewById(R.id.notes_list_view);
    }

    /**
     * Send a file path to a picture that will open up in the Image viewing activity.
     * @param filePath absolute file path to the image.
     */
    public void openUpImageViewer(String filePath)
    {
        File photoFile = new File(filePath);
        Uri data = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                photoFile);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, data);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

//        // Old code for using ImageEngine to view images
//        Intent viewImageIntent = new Intent(this, NewNoteActivity.class);
//        viewImageIntent.putExtra(MainActivity.FILE_PATH_KEY, filePath);
//        startActivity(viewImageIntent);
    }

}

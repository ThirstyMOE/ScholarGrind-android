package com.example.eyoon.collegegrind;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    private void retrieveNotesFromFileSystem()
    {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        List<File> files = getListFiles(storageDir);

    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * https://stackoverflow.com/questions/9530921/list-all-the-files-from-all-the-folder-in-a-single-list
     * @param parentDir
     * @return List of all files from the selected parent directory
     */
    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".csv")){
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
        String[] sampleStringArray = { "x", "y", "wqw", "falkwe", "a", "y", "weoriu", "weeooeeoo",
        "asdflkjlk", "asdfjkl;", "asdfjkl;asjiowe", "asl;kvnoweinvo;wienfokasjldfk", "aweirpoawiejvpoiawnevio0nawoeivnopawienvpoiawnepvoiawnepovinawpoeivnpaoweinvpo"};

        Note[] notes = { new Note("EY", "X"), new Note("OY", "Something") };

        ArrayAdapter<Note> notesAdapter = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, notes);

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
            Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_LONG).show();
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


}

package com.example.eyoon.collegegrind;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
// https://www.bignerdranch.com/blog/customizing-android-listview-rows-subclassing/
// https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
public class NoteAdapter extends ArrayAdapter<Note> {

    private Context mContext;
    private List<Note> notesList = new ArrayList<>();

    public NoteAdapter(@NonNull Context context, @LayoutRes ArrayList<Note> list) {
        super(context, 0, list);
        mContext = context;
        notesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.note_layout, parent, false);

        Note currentNote = notesList.get(position);

//        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
//        image.setImageResource(currentMovie.getmImageDrawable());

        TextView fileNameTextView = (TextView) listItem.findViewById(R.id.note_file_name_view);
        fileNameTextView.setText(currentNote.getFileName());

        Button noteDeleteButton = (Button) listItem.findViewById(R.id.notes_delete_button);
        noteDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view)
            {
                Log.e("TEST", "Clicked " + currentNote);
                deleteNote(currentNote);
            }
        });


//        TextView filePathTestView = (TextView) listItem.findViewById(R.id.test_alt_text_view);
//        filePathTestView.setText(currentNote.getFilePath());

        return listItem;
    }
    public boolean deleteNote(Note note)
    {
        // There is no picture inside the file, delete the empty file
        File currentPhotoFile = new File(note.getFilePath());
        // Deletion code Accessed at https://stackoverflow.com/questions/3554722/how-to-delete-internal-storage-file-in-android
        // on 9-25-2018
        boolean deleted = currentPhotoFile.delete();
        return deleted;
    }
}
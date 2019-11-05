package com.example.jeffrey.imtutor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    //private List<Upload> mUploads;
    public static String[] nameOfNotes={};
    public static String[] urlOfNotes={};

    StorageReference tempChildRef = NotesFragment.storageRef.child(RecyclerAdapter.clickClassName);
    StorageReference tempNoteRef;

    private Bitmap tempImage;

    public ImageAdapter(Context context) {
        mContext = context;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        holder.textViewName.setText(nameOfNotes[position]);
        Picasso.get()
                .load(urlOfNotes[position])
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

   @Override
    public int getItemCount() {
        return nameOfNotes.length;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(final View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    final int position = getAdapterPosition();

                    /*Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/

                    final EditText taskEditText = new EditText(mContext);
                    AlertDialog dialog = new AlertDialog.Builder(mContext)
                            .setTitle("Download the file?")
                            //.setMessage("What do you want to do next?")
                            //.setView(taskEditText)
                            .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).setNegativeButton("Cancel", null)
                                    .create();
                        dialog.show();

                }
            });
        }
    }
}
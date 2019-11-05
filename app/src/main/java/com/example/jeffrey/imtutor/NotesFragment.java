package com.example.jeffrey.imtutor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.admin.v1beta1.Progress;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class NotesFragment extends Fragment {
    @Nullable
    private FloatingActionButton addNewNote;
    public static StorageReference storageRef;
    public static FirebaseStorage storage;
    private ProgressDialog pd;
    private RecyclerView NotesView;
    private ImageAdapter mAdapter;
    private ProgressBar pc;
    private String[] names = {};
    private String[] urls = {};
    private ArrayList<NotesObj> list = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    //String uploadName;
    //private DatabaseReference mDatabaseRef;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes,container,false);

        //AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        NotesView = view.findViewById(R.id.notes_view);
        NotesView.setHasFixedSize(true);
        NotesView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pc = view.findViewById(R.id.progress_circle);

        addNewNote = view.findViewById(R.id.addNotes);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        pd = new ProgressDialog(getActivity());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    //Upload upload = postSnapshot.getValue(Upload.class);
                    //String temp = postSnapshot.getValue(String.class);
                    NotesObj temp = postSnapshot.getValue(NotesObj.class);
                    Log.d("what",temp.getNotesName());
                    list.add(temp);
                }
                ArrayList<String> names = new ArrayList<>();
                ArrayList<String> urls = new ArrayList<>();

                if(!list.isEmpty()) {
                    for (NotesObj note : list) {
                        if(note.getNotesClass().equals(RecyclerAdapter.clickClassName)){

                        names.add(note.getNotesName());
                        Log.d("???",note.getNotesName());
                        Log.d("num??",note.getNotesName());
                        urls.add(note.getImageUrl());
                        }
                    }
                }

                Log.d("ppp",ImageAdapter.nameOfNotes.toString());

                ImageAdapter.nameOfNotes = names.toArray(new String[0]);
                ImageAdapter.urlOfNotes = urls.toArray(new String[0]);
                mAdapter = new ImageAdapter(getActivity());

                NotesView.setAdapter(mAdapter);
                pc.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");





        addNewNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



                StorageReference classRef = storageRef.child(RecyclerAdapter.clickClassName);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);


            }
        });



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //Log.d("test ","successful");
        if(requestCode==2&&resultCode==RESULT_OK){
            final Uri uri = data.getData();
            final EditText taskEditText = new EditText(getActivity());
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Please Enter The Name of File")
                    //.setMessage("What do you want to do next?")
                    .setView(taskEditText)
                    .setPositiveButton("upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String title = String.valueOf(taskEditText.getText());

                            pd.setMessage("uploading...");
                            pd.show();



                            // StorageReference classRef = storageRef.child(RecyclerAdapter.clickClassName).child(uri.getLastPathSegment());
                            final StorageReference classChildRef = storageRef.child(RecyclerAdapter.clickClassName);
                            final StorageReference classRef = classChildRef.child(title);
                            classRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    pd.dismiss();

                                    classRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String downloadUri = uri.toString();

                                            NotesObj note = new NotesObj(title,downloadUri,RecyclerAdapter.clickClassName);
                                            String userId = databaseReference.push().getKey();
                                            databaseReference.child(userId).setValue(note);
                                            Log.d("biubiu",downloadUri);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();

          

        }
    }

}


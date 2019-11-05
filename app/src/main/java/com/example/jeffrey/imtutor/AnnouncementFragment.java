package com.example.jeffrey.imtutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class AnnouncementFragment extends Fragment {
    TextView textViewAnnouncementClassName;
    TextView textViewAnnouncementClassDescription;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //textViewProfileUserName = (TextView)findViewById(R.id.buttonLogin);
        View inf = inflater.inflate(R.layout.fragment_announcement,container,false);
        textViewAnnouncementClassName = (TextView) inf.findViewById(R.id.textViewAnnouncementClassName);
        textViewAnnouncementClassDescription = (TextView) inf.findViewById(R.id.textViewAnnouncementClassDescription);
        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
        Activity_signup.classRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ClassObject classObject = documentSnapshot.toObject(ClassObject.class);
                            textViewAnnouncementClassName.setText("Welcome to "+classObject.getClassName() + "!");
                            textViewAnnouncementClassDescription.setText("Class Description: "+ classObject.getClassDescription());
                        } else {
                            //Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Activity_homepage.this, "Error!", Toast.LENGTH_SHORT).show();
                        // Log.d(TAG, e.toString());
                    }
                });


        return inf;
    }


}

package com.example.jeffrey.imtutor;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ProfileFragment extends Fragment {
    TextView textViewProfileUserName;
    TextView textViewProfileUserEmail;
    TextView textViewProfileUserClasses;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //textViewProfileUserName = (TextView)findViewById(R.id.buttonLogin);
        View inf = inflater.inflate(R.layout.fragment_profile,container,false);
        textViewProfileUserName = (TextView) inf.findViewById(R.id.textViewProfileName);
        textViewProfileUserEmail = (TextView) inf.findViewById(R.id.textViewProfileEmail);
        textViewProfileUserClasses = (TextView) inf.findViewById(R.id.textViewProfileClasses);

        Activity_signup.userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            textViewProfileUserName.setText("User Name: "+user.getUserName());
                            textViewProfileUserEmail.setText("User Email: "+user.getUserEmail());
                            String userClass = "User Classes: ";
                            List<String> userClasses = user.getUserClasses();
                            for(int i = 0;i<userClasses.size();i++){
                                userClass = userClass + userClasses.get(i)+" ";
                            }
                            textViewProfileUserClasses.setText(userClass);
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

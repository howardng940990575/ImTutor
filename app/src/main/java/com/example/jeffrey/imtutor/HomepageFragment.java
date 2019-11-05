package com.example.jeffrey.imtutor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {


    //String[] s1 = {"test"};
    //String[] s2 = {"me2"};
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> detail = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.fragment_homepage,container,false);


        Activity_signup.userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User note = documentSnapshot.toObject(User.class);
                            List<String> classes = note.getUserClasses();
                            //Log.d(TAG, "getClasses:"+classes.size());
                            String classesArray[] = new String[classes.size()];
                            for(int i = 0;i<classes.size();i++){
                                classesArray[i]=classes.get(i);
                                //Log.d(TAG, "classesArray[]: "+classesArray[i]);
                                //Log.d(TAG, "classesArray: "+classes.get(i));
                            }

                            RecyclerAdapter.titles=classesArray;
                            RecyclerAdapter.details=classesArray;

                            recyclerView = (RecyclerView) result.findViewById(R.id.recycler_view);
                            //DatabaseHandler dbHandler;

                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);

                            adapter = new RecyclerAdapter();
                            recyclerView.setAdapter(adapter);

                           //getFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();
                            //RecyclerAdapter.titles= (String[]) classes.toArray();
                            //RecyclerAdapter.details= (String[]) classes.toArray();
                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, e.toString());
                    }
                });

        
        return result;
    }
}

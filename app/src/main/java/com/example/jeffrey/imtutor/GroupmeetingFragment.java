package com.example.jeffrey.imtutor;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class GroupmeetingFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MeetingAdapter myAdapter;

    ListView lvData;
    ArrayList<String> data = new ArrayList<>();
    public static List<Meeting> classMeetings;
    ArrayAdapter<String> adapter;
    public static Meeting meeting;
    public static int pos;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_groupmeeting,container,false);
        //lvData = (ListView) view.findViewById(R.id.lvData);
        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    classMeetings = getClass.getClassMeetings();
                    // classMeetings = getClass.getClassMeetings();
                    List<String> titles = new ArrayList<>() ;
                    List<String> contents = new ArrayList<>() ;

                    for(Meeting meeting : classMeetings){
                        Log.d("duck", "onSuccess: " +meeting.getMeetingTitle());

                        String title = meeting.getMeetingTitle();
                        data.add(title);
                        titles.add(meeting.getMeetingTitle());
                        contents.add(meeting.getMeetingDate()+"\n"+meeting.getMeetingTime()+"\n"+meeting.getOrganizer());
                    }

                    MeetingAdapter.Meetingtitles = titles.toArray(new String[0]);
                    MeetingAdapter.Meetingdetails = contents.toArray(new String[0]);

                    recyclerView = (RecyclerView) view.findViewById(R.id.meeting_recycler_view);
                    //DatabaseHandler dbHandler;

                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    myAdapter = new MeetingAdapter();
                    recyclerView.setAdapter(myAdapter);


                } else {
                    //Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        
                    }
                });
        
        return view;
    }
}

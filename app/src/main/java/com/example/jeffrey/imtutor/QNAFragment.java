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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QNAFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    QNAAdapter myAdapter;

    ListView lvDataQNA;
    ArrayList<String> data = new ArrayList<>();
    public static List<Post> classPosts;
    ArrayAdapter<String> adapter;
    public static Post post;
    public static int posQNA;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_q_and_a,container,false);
        //lvDataQNA = (ListView) view.findViewById(R.id.lvDataQNA);
        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    classPosts = getClass.getClassPosts();
                    // classMeetings = getClass.getClassMeetings();
                    List<Map<String, String>> dataSet = new ArrayList<Map<String, String>>();

                    List<String> titles = new ArrayList<>() ;
                    List<String> contents = new ArrayList<>() ;
                    for(Post post : classPosts){
                        //Log.d("duck", "onSuccess: " +post.getMeetingTitle());
                        Map<String, String> dataMap = new HashMap<String,String>(2);
                        dataMap.put("title",post.getPostTitle()+" - "+post.getPostTime() +" "+post.getPostDate());
                        dataMap.put("owner",post.getPostOwner());
                        dataSet.add(dataMap);
                        titles.add(post.getPostTitle());
                        contents.add(post.getPostContent());

                        //dataSet.add(dataMap);
                        String title = post.getPostTitle();
                        data.add(title);
                    }


                    QNAAdapter.QNAtitles = titles.toArray(new String[0]);
                    QNAAdapter.QNAdetails = contents.toArray(new String[0]);

                    recyclerView = (RecyclerView) view.findViewById(R.id.qna_recycler_view);
                    //DatabaseHandler dbHandler;

                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    myAdapter = new QNAAdapter();
                    recyclerView.setAdapter(myAdapter);

                } else {
                    //Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(Activity_homepage.this, "Error!", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, e.toString());
                    }
                });
        
        return view;
    }

}

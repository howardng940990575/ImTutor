package com.example.jeffrey.imtutor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Activity_view_QNA extends AppCompatActivity {
    ListView listViewPostReplies;
    TextView textViewPostTitle;
    TextView textViewPostOwner;
    TextView textViewPostDateAndTime;
    TextView textViewPostContent;
    Post post = QNAFragment.post;

    ArrayAdapter<String> adapter;
    User tempUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("click duck", "onSuccess: " + "IIIIGOOD??");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post_detail);

        listViewPostReplies = (ListView)findViewById(R.id.listViewPostReplies);
        textViewPostTitle = (TextView)findViewById(R.id.textViewPostTitle);
        textViewPostOwner = (TextView)findViewById(R.id.textViewPostOwner);
        textViewPostDateAndTime = (TextView)findViewById(R.id.textViewPostDateAndTime);
        textViewPostContent = (TextView)findViewById(R.id.textViewPostContent);
        Button buttonReplyToPost = (Button)findViewById(R.id.buttonReplyToPost);

        textViewPostTitle.setText(post.getPostTitle());
        textViewPostOwner.setText(post.getPostOwner());
        textViewPostDateAndTime.setText(post.getPostTime()+ " " + post.getPostDate());
        textViewPostContent.setText(post.getPostContent());

        //reply listview
        //ArrayList<String> data = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for(Reply reply : post.getPostReplies()){
            Map<String, String> dataMap = new HashMap<String,String>(2);
            dataMap.put("user",reply.getReplyUser()+" - "+reply.getReplyTime() +" "+reply.getReplyDate());
            dataMap.put("content",reply.getReplyContent());
            data.add(dataMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"user", "content"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
       /*adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                data
        );*/
        listViewPostReplies.setAdapter(adapter);
        //button
        buttonReplyToPost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reply clicked", "onClick: hahang");
                View view = (LayoutInflater.from(Activity_view_QNA.this)).inflate(R.layout.reply_pop_up,null);
                Log.d("reply clicked", "onClick: hahang");
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Activity_view_QNA.this);
                alertBuilder.setView(view);
                final EditText editTextReply = (EditText)view.findViewById(R.id.editTextReply);

                alertBuilder.setCancelable(true).setPositiveButton("Submit Reply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String replyContent = editTextReply.getText().toString();
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
                        String replyDate = df.format(Calendar.getInstance().getTime());
                        DateFormat tf = new SimpleDateFormat("HH:mm:ss z");
                        String replyTime = tf.format(Calendar.getInstance().getTime());
                        Reply newReply = new Reply(replyContent,replyDate,replyTime,Activity_login.userEmail);
                        List <Reply> newPostReplies = post.getPostReplies();
                        newPostReplies.add(newReply);
                        post.setPostReplies(newPostReplies);
                        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
                        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                                    List<Post> classPosts = getClass.getClassPosts();
                                    for(int i = 0;i<classPosts.size();i++){
                                        if(classPosts.get(i).getPostTitle().equals(post.getPostTitle())){
                                            classPosts.set(i,post);
                                        }
                                    }
                                    //classPosts.add(newPost);
                                    getClass.setClassPosts(classPosts);
                                    Activity_signup.classRef.set(getClass);


                                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                                    for(Reply reply : post.getPostReplies()){
                                        Map<String, String> dataMap = new HashMap<String,String>(2);
                                        dataMap.put("user",reply.getReplyUser()+" - "+reply.getReplyTime() +" "+reply.getReplyDate());
                                        dataMap.put("content",reply.getReplyContent());
                                        data.add(dataMap);
                                    }
                                    SimpleAdapter adapter = new SimpleAdapter(getApplication(), data,
                                            android.R.layout.simple_list_item_2,
                                            new String[] {"user", "content"},
                                            new int[] {android.R.id.text1,
                                                    android.R.id.text2});

                                    listViewPostReplies.setAdapter(adapter);
                                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new QNAFragment()).commit();
                                    //onBackPressed();
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
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }
}
package com.example.jeffrey.imtutor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_classdetail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    TextView email;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClass);
        setSupportActionBar(toolbar);

        this.setTitle(RecyclerAdapter.clickClassName);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new AnnouncementFragment()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutClass);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewClass);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutClass);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        email = (TextView) findViewById(R.id.textViewEmailClass);
        username = (TextView) findViewById(R.id.textViewUserNameClass);
        Activity_signup.userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User note = documentSnapshot.toObject(User.class);
                            String userName = note.getUserName();
                            String userEmail = note.getUserEmail();

                            email.setText(userEmail);
                            username.setText(userName);
                        } else {
                            Toast.makeText(Activity_classdetail.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Activity_classdetail.this, "Error!", Toast.LENGTH_SHORT).show();
                       
                    }
                });
        getMenuInflater().inflate(R.menu.activity_classdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Announcement) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new AnnouncementFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_QA) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new QNAFragment()).commit();
        } else if (id == R.id.nav_Groupmeeting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new GroupmeetingFragment()).commit();



        } else if (id == R.id.nav_Notes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new NotesFragment()).commit();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutClass);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addNewQuestion(View view) {


        
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new AddQuestionFragment()).commit();

    }
    public void submitNewQuestion(View view) {

        EditText newTitle = (EditText) findViewById(R.id.editTextPostTitle);
        
        EditText newContent = (EditText) findViewById(R.id.editTextPostContent);

        String nTitle = newTitle.getText().toString();
        String nContent = newContent.getText().toString();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        
        String nDate = df.format(Calendar.getInstance().getTime());
        DateFormat tf = new SimpleDateFormat("HH:mm:ss z");
        
        String nTime = tf.format(Calendar.getInstance().getTime());
        String nOwner = Activity_login.userEmail;
        List<Reply> nReplies = new ArrayList<>();


        final Post newPost = new Post(nTitle,nContent,nOwner,nDate,nTime,nReplies);
        

        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);

        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    List<Post> classPosts = getClass.getClassPosts();
                    classPosts.add(newPost);
                    getClass.setClassPosts(classPosts);
                    Activity_signup.classRef.set(getClass);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new QNAFragment()).commit();
                    //onBackPressed();
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


    }

    public void addNewMeeting(View view) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new AddgroupmeetingFragment()).commit();


    }

    public void submitNewMeeting(View view) {

        EditText newTitle = (EditText) findViewById(R.id.editTitle);
        //EditText newDate = (EditText) findViewById(R.id.editdate);
       // EditText newTime = (EditText) findViewById(R.id.editTime);
        EditText newLocation = (EditText) findViewById(R.id.editLocation);
        EditText newDetail = (EditText) findViewById(R.id.editDetail);

        Spinner newDateMonths = (Spinner)findViewById(R.id.spinnerMonths);



        String nTitle = newTitle.getText().toString();

        String nData = AddgroupmeetingFragment.month + " " +AddgroupmeetingFragment.date +" " + AddgroupmeetingFragment.year;
        String nTime = AddgroupmeetingFragment.Hours + ":" +AddgroupmeetingFragment.Minutes + " " +AddgroupmeetingFragment.AMPM;
        String nLocation = newLocation.getText().toString();
        String nDetail = newDetail.getText().toString();
        String nOwner = Activity_login.userEmail;
        List<String> nParticipants = new ArrayList<String>();


        final Meeting newMeeting = new Meeting(nTitle,nTime,nData,nLocation,nDetail,nOwner,nParticipants);
        Log.d("submitNewMeeting", nTitle+nData+nTime+nLocation+nDetail);

        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);

        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    List<Meeting> classMeetings = getClass.getClassMeetings();
                    classMeetings.add(newMeeting);
                    getClass.setClassMeetings(classMeetings);
                    Activity_signup.classRef.set(getClass);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_class,new GroupmeetingFragment()).commit();
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

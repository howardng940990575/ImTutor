package com.example.jeffrey.imtutor;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jeffrey.imtutor.Activity_login.userEmail;

public class Activity_homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomepageActivity";
    public static ArrayList<String> arList = new ArrayList<String>();
    EditText JoinNewClassName;



    //CollectionReference classes = db.collection();
    TextView email;
    TextView username;
    public static String userEmail;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        this.setTitle("ImTutor");

        

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();

        Activity_login.myRef.setValue(userEmail);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_homepage, menu);





        email = (TextView) findViewById(R.id.textViewUserEmail);
        username = (TextView) findViewById(R.id.textViewUserName);
        Activity_signup.userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User note = documentSnapshot.toObject(User.class);
                            String userName = note.getUserName();
                            userEmail = note.getUserEmail();
                            //String userEmail = note.getUserEmail();

                            email.setText(userEmail);
                            username.setText(userName);
                        } else {
                            Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Activity_homepage.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        //email.setText(userEmail);
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

        if(id == R.id.nav_myclass){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();

        }
          else if (id == R.id.nav_joinnewclass) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new JoinnewclassFragment()).commit();
                // Handle the camera action
        } else if (id == R.id.nav_profile) {

            //RecyclerAdapter.titles[0] = "duck";
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
        } else if (id == R.id.nav_logout) {
            Intent loginscreen=new Intent(this,Activity_login.class);
            loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginscreen);
            this.finish();

        }else if (id == R.id.nav_share) {
            String className = "JAPN2";
            Activity_signup.classRef = Activity_login.db.document("Classes/"+className);
            List<String> classTAs = new ArrayList<String>();
            List<User> classStudents = new ArrayList<User>();
            List<Meeting> classMeetings = new ArrayList<Meeting>();
            List<Post> classPosts = new ArrayList<Post>();
            classTAs.add("Jack");
            classTAs.add("Tom");
            classTAs.add("Alex");
            classTAs.add("Jeff");
           
            ClassObject newClass = new ClassObject(className,"First-Year Japanese"+"\n"+"\n"
                    +"Students carry out beginning-level tasks that involve listening, speaking, reading, and/or writing, and learn how to read and write 70 additional kanji" + "\n"+"\n"
                    +"Days & Time: "
                    +"MWF 08:00AM-09:05AM"+"\n"+"\n"
                    +"Location: "
                    +"R Carson Acad 242","Morimoto,M.",classTAs,classStudents,classMeetings,classPosts);
            Activity_signup.classRef.set(newClass);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void toClassDetailPage(){
        startActivity(new Intent(this, Activity_classdetail.class));

    }


    public void searchtheclass(View view){
        //Toast.makeText(Activity_homepage.this,"found the class!",Toast.LENGTH_SHORT).show();

        final Map<String, Object> myClass = new HashMap<>();



        JoinNewClassName = findViewById(R.id.EditMeetingTitle);
        final String tempName = JoinNewClassName.getText().toString().trim();

        if(!tempName.matches("")){
           
            myClass.put(tempName, tempName);

        Activity_signup.classRef = Activity_login.db.collection("Classes").document(tempName);
        Activity_signup.classRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "document ID" + document.getId());

                    if (document.exists()) {
                        //Howard 神code

                        Activity_signup.userRef.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            user = documentSnapshot.toObject(User.class);
                                            List<String> classes = user.getUserClasses();
                                            Map<String,Integer> points = user.getUserPoints();
                                            if(!classes.contains(tempName)){
                                                classes.add(tempName);
                                                points.put(tempName,0);
                                                user.setUserClasses(classes);
                                                user.setUserPoints(points);
                                                Activity_signup.userRef.set(user);
                                            }

                                        } else {
                                            Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Activity_homepage.this, "Error!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, e.toString());
                                    }
                                });
                      
                        Activity_signup.classRef.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                                            List<User> classStudents = getClass.getClassStudents();

                                            classStudents.add(user);
                                            getClass.setClassStudents(classStudents);
                                            Activity_signup.classRef.set(getClass);
                                        } else {
                                            Toast.makeText(Activity_homepage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Activity_homepage.this, "Error!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, e.toString());
                                    }
                                });
                        
                       Log.d(TAG, "Second if PASSED!!");
                        Activity_login.db.collection("UserEmail").document(userEmail).set(myClass,SetOptions.merge());

                        Toast.makeText(Activity_homepage.this,"found the class!",Toast.LENGTH_SHORT).show();
                        //
                        String tempName = Activity_login.userEmail;
                        Activity_login.docRef = Activity_login.db.collection("UserEmail").document(tempName);
                        Activity_login.docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Third if PASSED!!");
                                    DocumentSnapshot document = task.getResult();

                                    Log.d(TAG, "document ID" + document.getId());
                                    if (document.exists()) {//&&document!=null
                                        Log.d(TAG, "Forth if PASSED!!");
                                        Map<String, Object> mmap = document.getData();
                                        Log.d(TAG, "mmap" + mmap.size());
                                        int index = 0;
                                        for(Map.Entry<String,Object> map : mmap.entrySet()){
                                            Log.d(TAG, "Fifth if PASSED!!");
                                            String temp = map.getValue().toString();
                                            if(!arList.contains(temp)) {
                                                //RecyclerAdapter.titles[index] = temp;
                                                //index++;
                                                arList.add(temp);

                                            }

                                        }

                                        Log.d(TAG, "ArList" + arList);
                                        

                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();

                                    } else {
                                        //Toast.makeText(Activity_homepage.this,"there is no such class in the collection",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    //Toast.makeText(Activity_homepage.this,"get failed with",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                        //
                        //for(int i = 0; i < titles.length;i++){
                        int temp = arList.size();
                        Log.d(TAG, "arList.size()"+ temp);

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();

                    } else {
                        Toast.makeText(Activity_homepage.this,"there is no such class in the collection",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Toast.makeText(Activity_homepage.this,"get failed with",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    else{
            Toast.makeText(Activity_homepage.this,"No Input",Toast.LENGTH_SHORT).show();
        }


    }

}

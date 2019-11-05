package com.example.jeffrey.imtutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static android.view.View.VISIBLE;

public class Activity_login extends AppCompatActivity {


    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;


    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MyActivity";

    public FirebaseDatabase database;
    public static DatabaseReference myRef;

    public static DocumentReference docRef;


    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Log.d(TAG, "onCreate: good");

    }

    public void userLogin(View v) {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //toast.show();

            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Login in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: DENGLUCHENGGONG");
                            progressDialog.dismiss();

                            userEmail = editTextEmail.getText().toString().trim();
                            Activity_signup.userRef = db.document("Users/"+editTextEmail.getText().toString().trim());
                            //String id = myRef.push().getKey();
                            Activity_signup.userRef.get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                User note = documentSnapshot.toObject(User.class);
                                                List<String> classes = note.getUserClasses();
                                                Log.d(TAG, "getClasses:"+classes.size());
                                                String classesArray[] = new String[classes.size()];
                                                for(int i = 0;i<classes.size();i++){
                                                    classesArray[i]=classes.get(i);
                                                    Log.d(TAG, "classesArray[]: "+classesArray[i]);
                                                    Log.d(TAG, "classesArray: "+classes.get(i));
                                                }

                                                RecyclerAdapter.titles=classesArray;
                                                RecyclerAdapter.details=classesArray;
                                                //RecyclerAdapter.titles= (String[]) classes.toArray();
                                                //RecyclerAdapter.details= (String[]) classes.toArray();
                                            } else {
                                                Toast.makeText(Activity_login.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Activity_login.this, "Error!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }
                                    });

                            Toast.makeText(Activity_login.this, "Logged In", Toast.LENGTH_SHORT).show();
                            //登录成功！！
                            startActivity(new Intent(Activity_login.this, Activity_homepage.class));

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Activity_login.this, "Login failed. Please check if your Email and password is correct", Toast.LENGTH_SHORT).show();
                            /* 失败！ */
                        }
                    }
                });
    }

    /*@Override
    public void onClick(View v) {
        if(v == buttonLogin){
            Log.d(TAG, "onClick: good");
            userLogin();
        }
    }*/

    public void signup(View view) {
        startActivity(new Intent(Activity_login.this, Activity_signup.class));

    }

    public void forgotPassword(View view) {
        startActivity(new Intent(Activity_login.this, Activity_passwordreset.class));
    }



    @Override
    public void onPause() {
        super.onPause();
        savePreferences();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = editTextEmail.getText().toString();
        PasswordValue = editTextPassword.getText().toString();
        System.out.println("onPause save name: " + UnameValue);
        System.out.println("onPause save password: " + PasswordValue);
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        editor.commit();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        editTextEmail.setText(UnameValue);
        editTextPassword.setText(PasswordValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

}
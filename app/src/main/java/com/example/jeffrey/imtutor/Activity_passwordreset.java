package com.example.jeffrey.imtutor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_passwordreset extends AppCompatActivity {
    private EditText editTextEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordreset);

        editTextEmail = (EditText)findViewById(R.id.editTextResetEmail);
        resetPassword = (Button)findViewById(R.id.buttonReset);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = editTextEmail.getText().toString().trim();
                if(useremail.equals("")){
                    Toast.makeText(Activity_passwordreset.this, "Please enter your email",Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Activity_passwordreset.this, "Password reset email have been sent. Please check your mailbox.",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Activity_passwordreset.this,Activity_login.class));
                            }else{
                                Toast.makeText(Activity_passwordreset.this, "Failed to sent password reset email. Please check if your email is correct.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
    }


}

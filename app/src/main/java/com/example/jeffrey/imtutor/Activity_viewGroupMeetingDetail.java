package com.example.jeffrey.imtutor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;

public class  Activity_viewGroupMeetingDetail extends AppCompatActivity {

    TextView textViewMeetingTitle;
    TextView textViewMeetingDate;
    TextView textViewMeetingTime;
    TextView textViewMeetingLocation;
    TextView textViewMeetingOrganizer;
    TextView textViewMeetingParticipants;
    TextView textViewMeetingDetail;
    Meeting meeting = GroupmeetingFragment.meeting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("click duck", "onSuccess: " +"IIIIGOOD??");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_meeting_detail);
        textViewMeetingTitle = (TextView) findViewById(R.id.textViewMeetingTitle);
        textViewMeetingTime = (TextView) findViewById(R.id.textViewMeetingTime);
        textViewMeetingDate = (TextView) findViewById(R.id.textViewMeetingDate);
        textViewMeetingLocation = (TextView) findViewById(R.id.textViewMeetingLocation);
        textViewMeetingOrganizer = (TextView) findViewById(R.id.textViewMeetingOrganizer);
        textViewMeetingParticipants = (TextView) findViewById(R.id.textViewMeetingParticipants);
        textViewMeetingDetail = (TextView) findViewById(R.id.textViewMeetingDetail);



        textViewMeetingTitle.setText(textViewMeetingTitle.getText()+meeting.getMeetingTitle());
        textViewMeetingTime.setText(textViewMeetingTime.getText()+meeting.getMeetingTime());
        textViewMeetingDate.setText(textViewMeetingDate.getText()+meeting.getMeetingDate());
        textViewMeetingLocation.setText(textViewMeetingLocation.getText()+meeting.getMeetingLocation());
        textViewMeetingOrganizer.setText(textViewMeetingOrganizer.getText()+meeting.getOrganizer());
        //textViewMeetingParticipants.setText(textViewMeetingParticipants.getText()+meeting.getParticipants());
        String participants = "";
        for(String participant : meeting.getParticipants()){
            participants = participants + ", " + participant;
        }
        if(meeting.getParticipants().size()>0) {
            textViewMeetingParticipants.setText(textViewMeetingParticipants.getText() + participants.substring(1));
        }
        textViewMeetingDetail.setText(textViewMeetingDetail.getText()+meeting.getMeetingDetails());


    }

    public void joinMeeting(View view) {
        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    List<Meeting> tempClassMeetings = getClass.getClassMeetings();
                    // classMeetings = getClass.getClassMeetings();
                    List<String> participants = meeting.getParticipants();
                    if(participants.contains(Activity_homepage.userEmail)==false) {
                        participants.add(Activity_homepage.userEmail);
                        meeting.setParticipants(participants);

                        tempClassMeetings.set(GroupmeetingFragment.pos,meeting);

                        getClass.setClassMeetings(tempClassMeetings);

                        Activity_signup.classRef.set(getClass);

                        Activity_signup.userRef.get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {
                                            User tempUser = documentSnapshot.toObject(User.class);
                                            List<Meeting> userMeeting = tempUser.getUserMeetings();
                                            userMeeting.add(meeting);
                                            tempUser.setUserMeetings(userMeeting);
                                            Activity_signup.userRef.set(tempUser);

                                        } else {
                                            Toast.makeText(Activity_viewGroupMeetingDetail.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                        onBackPressed();
                        Toast.makeText(Activity_viewGroupMeetingDetail.this, "Joined!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Activity_viewGroupMeetingDetail.this, "Join Failed! already within the participants list!", Toast.LENGTH_SHORT).show();
                    }


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

    public void unjoinMeeting(View view) {
        Activity_signup.classRef = Activity_login.db.document("Classes/"+RecyclerAdapter.clickClassName);
        Activity_signup.classRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ClassObject getClass = documentSnapshot.toObject(ClassObject.class);
                    List<Meeting> tempClassMeetings = getClass.getClassMeetings();
                    // classMeetings = getClass.getClassMeetings();
                    List<String> participants = meeting.getParticipants();
                    participants.remove(Activity_homepage.userEmail);
                  

                    tempClassMeetings.set(GroupmeetingFragment.pos,meeting);

                    getClass.setClassMeetings(tempClassMeetings);

                    Activity_signup.classRef.set(getClass);

                    Activity_signup.userRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        User tempUser = documentSnapshot.toObject(User.class);
                                        List<Meeting> userMeeting = tempUser.getUserMeetings();
                                        for(int i = 0;i<userMeeting.size();i++){
                                            if(userMeeting.get(i).getMeetingTitle().equals(meeting.getMeetingTitle())){
                                                userMeeting.remove(i);
                                            }
                                        }
                                        tempUser.setUserMeetings(userMeeting);
                                        Activity_signup.userRef.set(tempUser);

                                    } else {
                                        Toast.makeText(Activity_viewGroupMeetingDetail.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    onBackPressed();

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
}

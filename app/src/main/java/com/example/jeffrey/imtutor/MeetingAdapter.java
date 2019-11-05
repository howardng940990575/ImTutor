package com.example.jeffrey.imtutor;





import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.jeffrey.imtutor.Activity_login.userEmail;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {


    private static final String TAG = "RecyclerAdapter";
    public static String[] Meetingtitles={"title"};
    public static String[] Meetingdetails ={"content"};

    //public static String clickClassName;


    String[] currentTitle;



    //int images = R.drawable.ic_classicon;



    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        //public ImageView itemImage;
        public TextView MeetingitemTitle;
        public TextView MeetingitemDetail;



        Map<String, Object> myClass = new HashMap<>();



        public ViewHolder(final View itemView) {
            super(itemView);
            //itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            MeetingitemTitle = (TextView)itemView.findViewById(R.id.meeting_title);
            MeetingitemDetail = (TextView)itemView.findViewById(R.id.meeting_content);






            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //String yourData = data.get(position);
                    GroupmeetingFragment.pos = position;
                    GroupmeetingFragment.meeting = GroupmeetingFragment.classMeetings.get(position);
                    //lvDataQNA.getContext().startActivity(new Intent(lvDataQNA.getContext(), Activity_view_QNA.class));

                    //clickClassName = titles[position];

                    //Log.d(TAG, "qqqqqq" + titles[position]);

                    itemView.getContext().startActivity(new Intent(itemView.getContext(), Activity_viewGroupMeetingDetail.class));




                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meeting_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.MeetingitemTitle.setText(Meetingtitles[i]);
        viewHolder.MeetingitemDetail.setText(Meetingdetails[i]);
        //viewHolder.itemImage.setImageResource(images);
    }

    @Override
    public int getItemCount() {
        return Meetingtitles.length;
    }
}


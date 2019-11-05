package com.example.jeffrey.imtutor;


/*import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;*/

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

public class QNAAdapter extends RecyclerView.Adapter<QNAAdapter.ViewHolder> {


    private static final String TAG = "RecyclerAdapter";
    public static String[] QNAtitles={"title"};
    public static String[] QNAdetails ={"content"};

    String[] currentTitle;

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        //public ImageView itemImage;
        public TextView QNAitemTitle;
        public TextView QNAitemDetail;



        Map<String, Object> myClass = new HashMap<>();



        public ViewHolder(final View itemView) {
            super(itemView);
            //itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            QNAitemTitle = (TextView)itemView.findViewById(R.id.qna_question);
            QNAitemDetail = (TextView)itemView.findViewById(R.id.qna_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //String yourData = data.get(position);
                    QNAFragment.posQNA = position;
                    QNAFragment.post = QNAFragment.classPosts.get(position);
                    itemView.getContext().startActivity(new Intent(itemView.getContext(), Activity_view_QNA.class));




                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.qna_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.QNAitemTitle.setText(QNAtitles[i]);
        viewHolder.QNAitemDetail.setText(QNAdetails[i]);
        //viewHolder.itemImage.setImageResource(images);
    }

    @Override
    public int getItemCount() {
        return QNAtitles.length;
    }
}



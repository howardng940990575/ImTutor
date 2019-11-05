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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private static final String TAG = "RecyclerAdapter";
    public static String[] titles={};
    public static String[] details ={};

    public static String clickClassName;


    String[] currentTitle;



    int images = R.drawable.ic_classicon;



    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;



        Map<String, Object> myClass = new HashMap<>();



        public ViewHolder(final View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);






            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    clickClassName = titles[position];

                    //Log.d(TAG, "qqqqqq" + titles[position]);

                    itemView.getContext().startActivity(new Intent(itemView.getContext(), Activity_classdetail.class));




                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        //Activity_login.docRef.get();
        //Log.d(TAG, "getnmsl" + arList);

       /*String tempName = Activity_login.userEmail;
       Activity_login.docRef = Activity_login.db.collection("UserEmail").document(tempName);
        Activity_login.docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Log.d(TAG, "document ID" + document.getId());
                    if (document.exists()&&document!=null) {

                        Map<String, Object> mmap = document.getData();
                        Log.d(TAG, "mmap" + mmap.size());
                        for(Map.Entry<String,Object> map : mmap.entrySet()){
                            String temp = map.getValue().toString();
                            arList.add(temp);
                            //String temp = document.getData().toString();
                           // arList.add(temp);
                            Log.d(TAG, "ArList" + map);
                            titles[0] = "sbbani";
                        }
                        titles[0] = "why buxing?";
                       //beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();
                        Log.d(TAG, "ArList" + titles[0]);
                        //titles[0] = "sbbani";
                        //String[] arListArray = (String[]) arList.toArray();
                        //currentTitle = arListArray;
                        Log.d(TAG, "ArList" + arList);
                        //Log.d(TAG, "currentTitle " + arListArray);
                        //Activity_login.db.collection("UserEmail").document(userEmail).update(myClass);

                        //Toast.makeText(Activity_homepage.this,"found the class!",Toast.LENGTH_SHORT).show();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomepageFragment()).commit();

                    } else {
                        //Toast.makeText(Activity_homepage.this,"there is no such class in the collection",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No such document");
                    }
                } else {
                    //Toast.makeText(Activity_homepage.this,"get failed with",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
        //arList.add("CS12A");
        //arList.add("CS12B");
        //String[] sbTitle = {arList.get(0),arList.get(1)};
        //titles = sbTitle;
        //titles = currentTitle;
        //titles[0] = "SHABIBA";
        //Log.d(TAG, "ArList" + titles[0]);
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}

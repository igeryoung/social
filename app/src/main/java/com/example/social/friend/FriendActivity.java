package com.example.social.friend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.Image.CircleTransform;
import com.example.social.R;
import com.example.social.model.PersonalInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
/*
    page for showing a list of user's friend
    item content : photo , name , about
 */
public class FriendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LinkedList<HashMap<String , String>> data;
    private MyAdapter myAdapter;
    private String account;
    private ArrayList<PersonalInformation> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        //get account and initial  friendList
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        friendList = (ArrayList<PersonalInformation>) intent.getSerializableExtra("friendList");

        //set the list ( recyclerView )
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //put data into a linkedlist to maintain list
        putData();

        //set the list by data in linkedlist
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    //put data into a linkedlist to maintain list
    private void putData() {
        data = new LinkedList<>();
        if(friendList == null)  return;
        for(int i = 0; i < friendList.size() ; i++){
            HashMap<String, String> row = new HashMap<>();
            row.put("name" , friendList.get(i).getName());
            row.put("uri" , friendList.get(i).getGraph());
            row.put("about" , friendList.get(i).getAbout());
            data.add(row);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        // set a holder and init it ( for maintain the list )
        class MyViewHolder extends RecyclerView.ViewHolder {
            public View itemView;
            public TextView name;
            public ImageView photo;
            public TextView about;
            public MyViewHolder(View v) {
                super(v);
                itemView = v;
                name = itemView.findViewById(R.id.name);
                photo = itemView.findViewById(R.id.photo);
                about = itemView.findViewById(R.id.about);

                //set up a click listener for clicking item in list
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PersonalInformation target = friendList.get(getAdapterPosition());
                        String info = target.getAllInString();
                        Intent next_page = new Intent(FriendActivity.this , FriendInfoActivity.class );
                        next_page.putExtra("friend_info" , info);
                        startActivity(next_page);
                    }
                });
            }
        }

        //show the list ( by construct a holder )
        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_friend, parent, false);
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;
        }

        //set data of item in list
        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.name.setText(data.get(position).get("name"));
            holder.about.setText(data.get(position).get("about"));

            //show photo by uri
            try {
                Picasso.get().load(data.get(position).get("uri")).transform(new CircleTransform()).into(holder.photo);
                //ImageButton.setImageURI();
            }
            catch(Exception e){
                Toast.makeText(FriendActivity.this, "no image", Toast.LENGTH_SHORT).show();
            }

        }

        //method for return size of list
        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    //Button "返回" click event : back to page SwipeActivity
    public void Back(View view) {
        finish();
    }
}
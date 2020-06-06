package com.example.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        friendList = (ArrayList<PersonalInformation>) intent.getSerializableExtra("friendList");

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        doData();

        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    private void doData() {
        data = new LinkedList<>();
        if(friendList == null)  return;
        for(int i = 0; i < friendList.size() ; i++){
            HashMap<String, String> row = new HashMap<>();
            row.put("name" , friendList.get(i).getName());
            row.put("uri" , friendList.get(i).getGraph());
            data.add(row);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        class MyViewHolder extends RecyclerView.ViewHolder {
            public View itemView;
            public TextView name;
            public MyViewHolder(View v) {
                super(v);
                itemView = v;
                name = itemView.findViewById(R.id.name);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "click " +getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        PersonalInformation target = friendList.get(getAdapterPosition());
                        String info = target.getAllInString();
                        Intent next_page = new Intent(FriendActivity.this , FriendInfoActivity.class );
                        next_page.putExtra("friend_info" , info);
                        startActivity(next_page);
                    }
                });
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_friend, parent, false);
            MyViewHolder vh = new MyViewHolder(itemView);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.name.setText(data.get(position).get("name"));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public void Back(View view) {
        finish();
    }
}
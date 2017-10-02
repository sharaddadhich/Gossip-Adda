package com.example.manoj.chatappusingfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.manoj.chatappusingfirebase.Adapters.UsersDispalyActivtyRecyclerViewAdapter;
import com.example.manoj.chatappusingfirebase.Interfaces.OnProfilePicClickListner;
import com.example.manoj.chatappusingfirebase.Interfaces.OnUserClickListner;
import com.example.manoj.chatappusingfirebase.POJO.UsersForChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class Users_Display_For_Chat_Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<UsersForChat> users = new ArrayList<UsersForChat>();

    RecyclerView recyclerView;
    UsersDispalyActivtyRecyclerViewAdapter rvadapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        firebaseAuth.signOut();
        finish();
        Intent i = new Intent(Users_Display_For_Chat_Activity.this,LoginActivity.class);
        startActivity(i);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_display);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

        rvadapter = new UsersDispalyActivtyRecyclerViewAdapter(this, users, new OnUserClickListner() {
            @Override
            public void onUserClicked(String uid, String Username) {
                Intent chat = new Intent(Users_Display_For_Chat_Activity.this, ChatActivity.class);
                chat.putExtra("uid", uid);
                chat.putExtra("Username", Username);
                startActivity(chat);
            }
        },
                new OnProfilePicClickListner() {
                    @Override
                    public void onProfilePicClicked(String Username, String ProfilePicUrl) {
                        Intent fullscreen = new Intent(Users_Display_For_Chat_Activity.this, FullSize_PicActivity.class);
                        Bundle data = new Bundle();
                        data.putString("Username", Username);
                        data.putString("Picurl", ProfilePicUrl);
                        fullscreen.putExtra("data", data);
                        startActivity(fullscreen);
                    }
                }
        );


        recyclerView = (RecyclerView) findViewById(R.id.rv_UsersForChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvadapter);
        retrivedata();

    }

    public void retrivedata()
    {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UsersForChat usersForChat = dataSnapshot.getValue(UsersForChat.class);
                Log.d("123123", "onChildAdded: user " +usersForChat.getName() + usersForChat.getPhoneNo() + usersForChat.getProfile_Pic_Url());
                if(firebaseAuth.getCurrentUser().getUid().toString().equals(usersForChat.getId()))
                {
                    //Do nothing
                }
                else
                {
                    users.add(usersForChat);
                    rvadapter.UpdateArrayList(users);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

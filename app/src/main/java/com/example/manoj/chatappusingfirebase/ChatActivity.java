package com.example.manoj.chatappusingfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manoj.chatappusingfirebase.Adapters.ChatDispalyAdapterRecyclerView;
import com.example.manoj.chatappusingfirebase.POJO.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rvchatDisplay;
    EditText etMessage;
    ImageButton ibtnSendMessage;
    String uid;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1,databaseReference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        uid = getIntent().getStringExtra("uid");
        Log.d("345345", "onCreate: firebaeid" + firebaseAuth.getCurrentUser().getUid().toString());
        databaseReference1 = firebaseDatabase.getReference().child("Messages").child(firebaseAuth.getCurrentUser().getUid().toString()+uid);
        databaseReference2 = firebaseDatabase.getReference().child("Messages").child(uid+firebaseAuth.getCurrentUser().getUid().toString());


        rvchatDisplay = (RecyclerView) findViewById(R.id.rv_ChatActivity);
        etMessage = (EditText) findViewById(R.id.et_Message);
        ibtnSendMessage = (ImageButton) findViewById(R.id.ibtn_SendMessage);

        rvchatDisplay.setLayoutManager(new LinearLayoutManager(this));
        final ChatDispalyAdapterRecyclerView radapter = new ChatDispalyAdapterRecyclerView(this,messages);
        rvchatDisplay.setAdapter(radapter);

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().length()>0)
                {
                    ibtnSendMessage.setClickable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ibtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();
                ChatMessage thisMessage1 = new ChatMessage(message+"_s",null);
                ChatMessage thisMessage2 = new ChatMessage(message+"_r",null);

                databaseReference1.push().setValue(thisMessage1);
                databaseReference2.push().setValue(thisMessage2);
                etMessage.setText("");

            }
        });



        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage thisMessage = dataSnapshot.getValue(ChatMessage.class);
                Log.d("987987", "onChildAdded: "+ thisMessage.getMessage() + thisMessage.getPicurl());
                messages.add(thisMessage);
                radapter.DataSetChanged(messages);


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

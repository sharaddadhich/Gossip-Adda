package com.example.manoj.chatappusingfirebase;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.example.manoj.chatappusingfirebase.Interfaces.OnChatPicClickListner;
import com.example.manoj.chatappusingfirebase.POJO.ChatMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rvchatDisplay;
    EditText etMessage;
    ImageButton ibtnSendMessage,ibtnSendImage;
    String uid,Username;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    StorageReference ChatPhotoReference;
    DatabaseReference databaseReference1,databaseReference2;

    public static final int RequestImage = 1123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        uid = getIntent().getStringExtra("uid");
        Username = getIntent().getStringExtra("Username");
        getSupportActionBar().setTitle(Username);
        Log.d("345345", "onCreate: firebaeid" + firebaseAuth.getCurrentUser().getUid().toString());
        databaseReference1 = firebaseDatabase.getReference().child("Messages").child(firebaseAuth.getCurrentUser().getUid().toString()+uid);
        databaseReference2 = firebaseDatabase.getReference().child("Messages").child(uid+firebaseAuth.getCurrentUser().getUid().toString());

        firebaseStorage = FirebaseStorage.getInstance();
        ChatPhotoReference = firebaseStorage.getReference().child("ChatPics");

        rvchatDisplay = (RecyclerView) findViewById(R.id.rv_ChatActivity);
        etMessage = (EditText) findViewById(R.id.et_Message);
        ibtnSendMessage = (ImageButton) findViewById(R.id.ibtn_SendMessage);
        ibtnSendImage = (ImageButton) findViewById(R.id.ibtn_SendImage);

        rvchatDisplay.setLayoutManager(new LinearLayoutManager(this));
        final ChatDispalyAdapterRecyclerView radapter = new ChatDispalyAdapterRecyclerView(this, messages, new OnChatPicClickListner() {
            @Override
            public void onPicClicked(String Url) {
                Intent newIntent = new Intent(ChatActivity.this,FullSize_PicActivity.class);
                Bundle data = new Bundle();
                data.putString("Username", Username);
                data.putString("Picurl", Url);
                newIntent.putExtra("data", data);
                startActivity(newIntent);
            }
        });
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

        ibtnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thisIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                thisIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(thisIntent,"Select Image"),RequestImage);
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
                rvchatDisplay.smoothScrollToPosition(messages.size());


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK &&  requestCode == RequestImage)
        {
            Uri datauri = data.getData();
            StorageReference storageReference = ChatPhotoReference.child(datauri.toString());
            storageReference.putFile(datauri).addOnSuccessListener(this,
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri DownloadUri = taskSnapshot.getDownloadUrl();
                            ChatMessage thisMessage = new ChatMessage(null,DownloadUri+"_s");
                            ChatMessage tMessage = new ChatMessage(null,DownloadUri+"_r");
                            databaseReference1.push().setValue(thisMessage);
                            databaseReference2.push().setValue(tMessage);
                        }
                    });
        }
    }
}

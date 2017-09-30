package com.example.manoj.chatappusingfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    String PhotoUrl;

    EditText etName,etPhoneNo;
    Button btnGotoChat;
    ImageView ivProfilePhoto;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ProgressDialog PdforImageUpload;
    ProgressDialog PdforGotoChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Profile_Photo");

        etName = (EditText) findViewById(R.id.et_ProfileName);
        etPhoneNo = (EditText) findViewById(R.id.et_ProfilePhoneNo);
        btnGotoChat = (Button) findViewById(R.id.btn_profileSubmit);
        ivProfilePhoto = (ImageView) findViewById(R.id.iv_ProfilePhoto);
        btnGotoChat.setEnabled(false);

        PdforGotoChatButton = new ProgressDialog(this);

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadformgallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                uploadformgallery.setType("image/*");
                startActivityForResult(uploadformgallery,1);
            }
        });


        btnGotoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitProfile();
                Intent gotoUsers = new Intent(ProfileActivity.this,Users_Display_For_Chat_Activity.class);
                finish();
                startActivity(gotoUsers);
            }
        });

    }

    public void SubmitProfile()
    {
        String Name = etName.getText().toString();
        String PhoneNo = etPhoneNo.getText().toString();

        if(Name.trim().length()==0)
        {
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(PhoneNo.trim().length()==0)
        {
            Toast.makeText(this, "Please Provide Your Phone No", Toast.LENGTH_SHORT).show();
            return;
        }

        PdforGotoChatButton.setMessage("Submitting Your Details");
        PdforGotoChatButton.show();
        String Uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(Uid);
        databaseReference.child(Uid).child("name").setValue(Name);
        databaseReference.child(Uid).child("phoneNo").setValue(PhoneNo);
        databaseReference.child(Uid).child("id").setValue(firebaseAuth.getCurrentUser().getUid());
        Log.d("234234", "SubmitProfile: " + firebaseAuth.getCurrentUser().getUid());

        PdforGotoChatButton.dismiss();
        Toast.makeText(this, "Sucessfully Submitted Your Details", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            Uri TargetUri = data.getData();
            Glide.with(this).load(TargetUri).into(ivProfilePhoto);
            StorageReference stref = storageReference.child("Profile_Pic"+firebaseAuth.getCurrentUser().getUid());
            stref.putFile(TargetUri).addOnSuccessListener(ProfileActivity.this,
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            PhotoUrl = taskSnapshot.getDownloadUrl().toString();
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).
                                    child("profile_Pic_Url").setValue(PhotoUrl);
                            btnGotoChat.setEnabled(true);
                        }
                    });


        }
    }
}

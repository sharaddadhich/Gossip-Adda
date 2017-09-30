package com.example.manoj.chatappusingfirebase;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullSize_PicActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size__pic);

        Bundle data = getIntent().getBundleExtra("data");
        String username = data.getString("Username");
        String url = data.getString("Picurl");

        tvUsername = (TextView) findViewById(R.id.tv_FullSizeUSername);
        ivPic = (ImageView) findViewById(R.id.iv_FullSize);

        tvUsername.setText(username);
        Glide.with(this).load(url).into(ivPic);


    }
}





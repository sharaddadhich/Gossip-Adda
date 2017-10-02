package com.example.manoj.chatappusingfirebase.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manoj.chatappusingfirebase.Interfaces.OnProfilePicClickListner;
import com.example.manoj.chatappusingfirebase.Interfaces.OnUserClickListner;
import com.example.manoj.chatappusingfirebase.POJO.UsersForChat;
import com.example.manoj.chatappusingfirebase.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


/**
 * Created by Manoj on 8/1/2017.
 */



public class UsersDispalyActivtyRecyclerViewAdapter extends
        RecyclerView.Adapter<UsersDispalyActivtyRecyclerViewAdapter.UserViewHolder> {
    FirebaseAuth firebaseAuth;
    ArrayList<UsersForChat> users;
    Context context;
    OnUserClickListner onUserClickListner;
    OnProfilePicClickListner onProfilePicClickListner;

    public UsersDispalyActivtyRecyclerViewAdapter(Context context,ArrayList<UsersForChat> users,OnUserClickListner onUserClickListner
    , OnProfilePicClickListner onProfilePicClickListner) {
        this.users = users;
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        this.onUserClickListner = onUserClickListner;
        this.onProfilePicClickListner = onProfilePicClickListner;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View thisView = li.inflate(R.layout.users_registered_display,parent,false);
        return new UserViewHolder(thisView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        final UsersForChat thisUser = users.get(position);
        holder.tvUserName.setText(thisUser.getName());
        Glide.with(context).load(thisUser.getProfile_Pic_Url()).into(holder.ivUserProfilePic);

        holder.ivUserProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProfilePicClickListner.onProfilePicClicked(thisUser.getName(),thisUser.getProfile_Pic_Url());
            }
        });
        holder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserClickListner.onUserClicked(thisUser.getId(),thisUser.getName());
            }
        });
;


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void UpdateArrayList(ArrayList<UsersForChat> users)
    {
        this.users = users;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder
    {

        TextView  tvUserName;
        ImageView ivUserProfilePic;
        View thisView;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_ChatUserName);
            ivUserProfilePic = (ImageView) itemView.findViewById(R.id.iv_ChatUserImage);
            thisView = itemView;


        }
    }

}

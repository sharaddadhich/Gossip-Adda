package com.example.manoj.chatappusingfirebase.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.manoj.chatappusingfirebase.Interfaces.OnChatPicClickListner;
import com.example.manoj.chatappusingfirebase.Interfaces.OnProfilePicClickListner;
import com.example.manoj.chatappusingfirebase.POJO.ChatMessage;
import com.example.manoj.chatappusingfirebase.R;
import java.util.ArrayList;

/**
 * Created by sharaddadhich on 21/09/17.
 */

public class ChatDispalyAdapterRecyclerView extends RecyclerView.Adapter<ChatDispalyAdapterRecyclerView.ChatViewHolder> {

    Context context;
    ArrayList<ChatMessage> chat;
    OnChatPicClickListner onChatPicClickListner;

    public ChatDispalyAdapterRecyclerView(Context context, ArrayList<ChatMessage> chat, OnChatPicClickListner onChatPicClickListner) {
        this.context = context;
        this.chat = chat;
        this.onChatPicClickListner = onChatPicClickListner;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage thisMessage = chat.get(position);
        if(thisMessage.getPicurl()==null)
        {
            if(thisMessage.getMessage().charAt(thisMessage.getMessage().length()-1)=='s')
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            return 2;
        }

    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View thisView = null;
        if(viewType==0)
        {
            thisView = li.inflate(R.layout.sendermessage,parent,false);
        }
        else if(viewType==1)
        {
            thisView = li.inflate(R.layout.recievermessage,parent,false);
        }
        else if(viewType==2)
        {
            thisView = li.inflate(R.layout.chatimagelayout,parent,false);
        }


        return new ChatViewHolder(thisView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        final ChatMessage thisMessage = chat.get(position);
        if(thisMessage.getPicurl()==null)
        {
            holder.tvMessage.setText(thisMessage.getMessage().substring(0,thisMessage.getMessage().length()-2));
        }
        else
        {
            if(thisMessage.getPicurl().charAt(thisMessage.getPicurl().length()-1)=='s')
            {
                holder.ivRecievedImage.setVisibility(View.INVISIBLE);
                Glide.with(context).load(thisMessage.getPicurl().substring(0,thisMessage.getPicurl().length()-2)).into(holder.ivSentImage);
                holder.ivSentImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onChatPicClickListner.onPicClicked(thisMessage.getPicurl().substring(0,thisMessage.getPicurl().length()-2));
                    }
                });
            }
            else
            {
                holder.ivSentImage.setVisibility(View.INVISIBLE);
                Glide.with(context).load(thisMessage.getPicurl().substring(0,thisMessage.getPicurl().length()-2)).into(holder.ivRecievedImage);
                holder.ivRecievedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onChatPicClickListner.onPicClicked(thisMessage.getPicurl().substring(0,thisMessage.getPicurl().length()-2));
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public void DataSetChanged(ArrayList<ChatMessage> messages)
    {
        this.chat = messages;
        notifyDataSetChanged();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        ImageView ivSentImage,ivRecievedImage;
        TextView tvMessage;
        LinearLayout llsender;

        public ChatViewHolder(View itemView) {
            super(itemView);
            ivSentImage = (ImageView) itemView.findViewById(R.id.iv_sentImage);
            ivRecievedImage = (ImageView) itemView.findViewById(R.id.iv_recievedImage);
            llsender = (LinearLayout) itemView.findViewById(R.id.ll_sender);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_Message);
        }
    }

}


package com.example.manoj.chatappusingfirebase.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.manoj.chatappusingfirebase.POJO.ChatMessage;
import com.example.manoj.chatappusingfirebase.R;
import java.util.ArrayList;

/**
 * Created by sharaddadhich on 21/09/17.
 */

public class ChatDispalyAdapterRecyclerView extends RecyclerView.Adapter<ChatDispalyAdapterRecyclerView.ChatViewHolder> {

    Context context;
    ArrayList<ChatMessage> chat;

    public ChatDispalyAdapterRecyclerView(Context context, ArrayList<ChatMessage> chat) {
        this.context = context;
        this.chat = chat;
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


        return new ChatViewHolder(thisView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatMessage thisMessage = chat.get(position);
        holder.tvMessage.setText(thisMessage.getMessage().substring(0,thisMessage.getMessage().length()-2));
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

        TextView tvMessage;
        LinearLayout llsender;

        public ChatViewHolder(View itemView) {
            super(itemView);
            llsender = (LinearLayout) itemView.findViewById(R.id.ll_sender);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_Message);
        }
    }

}


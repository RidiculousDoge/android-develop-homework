package com.example.chapter3.homework;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FriendAdapter extends RecyclerView.Adapter<frined_list_holder> {
    @NonNull
    private List<String> mFriends=new ArrayList<>();
    public FriendAdapter(List<String> frineds){
        mFriends=frineds;
    }
    @NonNull
    @Override
    public frined_list_holder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= View.inflate(parent.getContext(),R.layout.friend_text,null);
        return new frined_list_holder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull frined_list_holder holder,int position){
        holder.bind(mFriends.get(position));
    }
    @Override
    public int getItemCount(){return mFriends.size();}

}

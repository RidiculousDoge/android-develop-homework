package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<TextViewHolder>{
    @NonNull
    private List<String> mItems = new ArrayList<>();

    public SearchAdapter(List<String> items){
        this.mItems=items;
    }
    //这个方法用于创建条目的View
    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        //传进去的view就是条目的界面
        View view=View.inflate(parent.getContext(),R.layout.layout_text,null);
        return new TextViewHolder(view);
    }

    //这个方法是用来绑定holder的，一般用来设置数据
    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        //设置数据
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public void notifyItems(@NonNull List<String> items){
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
}

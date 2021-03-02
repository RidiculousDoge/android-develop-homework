package com.example.helloworld;

import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mTextView;
    public TextViewHolder(@NonNull View itemView){
        super(itemView);
        //找到条目的控件
        mTextView=itemView.findViewById(R.id.recyclerView_text);
        itemView.setOnClickListener(this);
    }
    //这个方法用于设置数据
    public void bind(String text){
        //设置数据
        mTextView.setText(text);
    }
    @Override
    public void onClick(View v){
        Intent intent=new Intent(v.getContext(),LinkedActivity.class);
        intent.putExtra("extra",mTextView.getText().toString());
        v.getContext().startActivity(intent);
    }
}

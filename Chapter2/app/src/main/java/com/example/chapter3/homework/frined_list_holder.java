package com.example.chapter3.homework;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class frined_list_holder extends RecyclerView.ViewHolder {
    private TextView mtextView;
    public frined_list_holder(@NonNull View itemView){
        super(itemView);
        mtextView=itemView.findViewById(R.id.frined_text);
    }
    public void bind(String text){
        mtextView.setText(text);
    }
}

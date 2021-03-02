package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//constructive function
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("MainActivity","onCreate execute");
        //找到控件
        mRecyclerView =findViewById(R.id.recyclerView);

        //准备数据
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(String.valueOf("第"+i+"个数据"));
        }
        //创建适配器
        SearchAdapter mSearchAdapter=new SearchAdapter(items);
        //设置到recyclerView里去
        mRecyclerView.setAdapter(mSearchAdapter);
        //RecyclerView需要设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mSearchAdapter.notifyItems(items);

    }
}







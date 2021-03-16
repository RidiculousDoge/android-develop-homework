package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private LottieAnimationView animationView;
    private RecyclerView mRecyclerView;
    private AnimatorSet animatorSet;
    private ObjectAnimator alphaAnimator_recyclerView;
    private ObjectAnimator alphaAnimator_lottie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View root= inflater.inflate(R.layout.fragment_placeholder, container, false);
        animationView=root.findViewById(R.id.lottie_view);
        animationView.playAnimation();
        animatorSet=new AnimatorSet();
        List<String> items=new ArrayList<>();
        for(int i=0;i<100;i++){
            String tmp="好友"+(i+1);
            items.add(tmp);
        }
        mRecyclerView=root.findViewById(R.id.recyclerView);
        FriendAdapter mfriendAdapter=new FriendAdapter(items);
        mRecyclerView.setAdapter(mfriendAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        mRecyclerView.setAlpha(0f);
        animationView.setAlpha(1f);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                // 淡出lottie
                alphaAnimator_lottie=ObjectAnimator.ofFloat(animationView,
                        "scaleX",0f);
                alphaAnimator_recyclerView=ObjectAnimator.ofFloat(mRecyclerView,
                        "alpha",1f);
                animatorSet.play(alphaAnimator_lottie).with(alphaAnimator_recyclerView);
                animatorSet.start();
            }
        }, 5000);

    }
}

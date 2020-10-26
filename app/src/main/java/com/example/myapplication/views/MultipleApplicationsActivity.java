package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RvAdapter;

public class MultipleApplicationsActivity extends AppCompatActivity {

    private RecyclerView rvHolder;
    private RvAdapter rvAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourcards_view);
        rvHolder=findViewById(R.id.rvHolder);

        rvHolder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvHolder.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                rvAdapter =new RvAdapter(getApplicationContext(),1,rvHolder.getMeasuredWidth(),itemClickListener);
                rvHolder.setAdapter(rvAdapter);

            }
        });
        findViewById(R.id.closBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    itemClickListener itemClickListener=new itemClickListener() {
        @Override
        public void itemClicked() {

        }
    };

    public interface itemClickListener{
        void itemClicked();
    }
}
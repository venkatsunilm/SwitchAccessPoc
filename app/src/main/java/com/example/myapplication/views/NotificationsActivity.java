package com.example.myapplication.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.RvAdapter;

public class NotificationsActivity extends AppCompatActivity {

    private RvAdapter rvAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView=findViewById(R.id.recylerNotificationsView);

        rvAdapter=new RvAdapter(this);
        recyclerView.setAdapter(rvAdapter);
    }
}

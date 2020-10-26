package com.example.myapplication.views;

import android.os.Bundle;
import android.util.Log;
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
                rvAdapter =new RvAdapter(getApplicationContext(),1,rvHolder.getMeasuredWidth());

                rvHolder.setAdapter(rvAdapter);

            }
        });

    }
}

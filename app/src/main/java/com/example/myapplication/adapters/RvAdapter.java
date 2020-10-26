package com.example.myapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Commons;
import com.example.myapplication.R;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

     private Context mContext;
     private int viewType=0;
     private int viewWidth=0;
     private static final int SIZE=3;

     public RvAdapter(Context context,int type,int width){
         this.mContext=context;
         viewType=type;
         viewWidth = width;
     }

    public RvAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewTypes) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        if(viewType ==0) {
            View view = layoutInflater.inflate(R.layout.inflate_notifications_item, parent, false);
            return new ViewHolder(view, mContext);
        }
        else{
            View view = layoutInflater.inflate(R.layout.inflate_cards, parent, false);
            view.getLayoutParams().width= (int) (viewWidth/SIZE - (Commons.convertDpToPixel(40f,mContext)));
            return new ViewHolder2(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
         if(viewType==0)
        return 10;

         return SIZE;
    }

    public View preView=null;

    class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {

        }
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayout;
        private View view;
        private Button button;
        private Context mContext;


        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            mContext=context;
            linearLayout = itemView.findViewById(R.id.linearLayout);
            view = itemView.findViewById(R.id.view);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button) {
                linearLayout.setVisibility(View.VISIBLE);
            }

            if(preView ==null){
                preView=view;
                Log.e("was","it null");
            }else{
                preView.setBackgroundColor((ContextCompat.getColor(mContext,R.color.grey)));
                preView=view;
            }
            view.setBackgroundColor(ContextCompat.getColor(mContext,R.color.black));
        }
    }
}
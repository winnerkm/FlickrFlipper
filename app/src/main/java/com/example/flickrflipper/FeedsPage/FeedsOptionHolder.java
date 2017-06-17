package com.example.flickrflipper.FeedsPage;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.flickrflipper.R;

public class FeedsOptionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private FeedsRecyclerAdapter mAdapter;

    ImageView leftImageView;
    ImageView rightImageView;
    LinearLayout container;
    LinearLayout container1;

    public FeedsOptionHolder(View itemView, FeedsRecyclerAdapter feedsRecyclerAdapter) {
        super(itemView);

        container = (LinearLayout) itemView.findViewById(R.id.container);
        container1 = (LinearLayout) itemView.findViewById(R.id.container1);
        leftImageView = (ImageView) itemView.findViewById(R.id.left_IV);
        rightImageView = (ImageView) itemView.findViewById(R.id.right_IV);

        itemView.setOnClickListener(this);
        mAdapter = feedsRecyclerAdapter;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        mAdapter.isClicked(getAdapterPosition());
    }
}

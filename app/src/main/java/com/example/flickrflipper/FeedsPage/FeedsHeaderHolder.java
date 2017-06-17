package com.example.flickrflipper.FeedsPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.flickrflipper.R;

public class FeedsHeaderHolder extends RecyclerView.ViewHolder {
    TextView headerTextView;
    public FeedsHeaderHolder(View itemView) {
        super(itemView);
        headerTextView = (TextView) itemView.findViewById(R.id.header_tv);
    }
}

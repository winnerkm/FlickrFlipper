package com.example.flickrflipper.FeedsPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.flickrflipper.R;

public class FeedsFooterHolder extends RecyclerView.ViewHolder {

    TextView footerTV;

    public FeedsFooterHolder(View itemView) {
        super(itemView);
        footerTV = (TextView) itemView.findViewById(R.id.footer_tv);
    }
}

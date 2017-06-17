package com.example.flickrflipper.FeedsPage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.flickrflipper.R;

public class FeedsExpandedItemHolder extends RecyclerView.ViewHolder {

    TextView nameTV;
    TextView descriptionTV;
    TextView dimensionTV;


    public FeedsExpandedItemHolder(View itemView) {
        super(itemView);
        nameTV = (TextView) itemView.findViewById(R.id.name_tv);
        descriptionTV = (TextView) itemView.findViewById(R.id.description_tv);
        dimensionTV = (TextView) itemView.findViewById(R.id.dimension_tv);

    }
}

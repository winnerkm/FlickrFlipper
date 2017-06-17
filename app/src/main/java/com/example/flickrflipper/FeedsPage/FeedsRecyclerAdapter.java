package com.example.flickrflipper.FeedsPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flickrflipper.model.FlickrResponse;
import com.example.flickrflipper.R;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FeedsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_OPTION_LEFT = 0;
    public static final int ITEM_TYPE_OPTION_RIGHT = 1;
    public static final int ITEM_TYPE_HEADER = 2;
    public static final int ITEM_TYPE_FOOTER = 3;
    public static final int ITEM_TYPE_EXPANDED = 4;


    private Context mContext;
    private FlickrResponse mFlickrResponse;
    private boolean isShowDetails;
    private int showDetailsPosition;
    private int adapterClickPosition;


    public FeedsRecyclerAdapter(Context context, FlickrResponse flickrResponse) {
        this.mContext = context;
        this.mFlickrResponse = flickrResponse;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = null;
        switch (viewType) {
            case ITEM_TYPE_OPTION_LEFT:
                inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_item_left, parent, false);
                return new FeedsOptionHolder(inflatedView, FeedsRecyclerAdapter.this);
            case ITEM_TYPE_OPTION_RIGHT:
                inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_item_right, parent, false);
                return new FeedsOptionHolder(inflatedView, FeedsRecyclerAdapter.this);

            case ITEM_TYPE_HEADER:
                inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_header, parent, false);
                return new FeedsHeaderHolder(inflatedView);

            case ITEM_TYPE_FOOTER:
                inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_footer, parent, false);
                return new FeedsFooterHolder(inflatedView);

            case ITEM_TYPE_EXPANDED:
                inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeds_expanded_item, parent, false);
                return new FeedsExpandedItemHolder(inflatedView);

        }
        return new FeedsFooterHolder(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int mDesTextIconListPos = position - 1 - (position >= showDetailsPosition && isShowDetails ? 1 : 0);
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_HEADER:
                FeedsHeaderHolder feedsHeaderHolder = (FeedsHeaderHolder) holder;
                feedsHeaderHolder.headerTextView.setText(mFlickrResponse.getTitle());
                break;

            case ITEM_TYPE_OPTION_LEFT:
                if (isShowDetails && adapterClickPosition == position) {

                    FeedsOptionHolder incidentSubTypeOptionHolderLeft = (FeedsOptionHolder) holder;
                    incidentSubTypeOptionHolderLeft.container.setBackground(mContext.getResources().getDrawable(R.drawable.border));
                    Picasso.with(mContext).load(mFlickrResponse.getItems().get(mDesTextIconListPos).getMedia().getM())
                            .into(incidentSubTypeOptionHolderLeft.leftImageView);
                } else {

                    FeedsOptionHolder incidentSubTypeOptionHolderLeft = (FeedsOptionHolder) holder;
                    incidentSubTypeOptionHolderLeft.container.setBackground(null);

                    Picasso.with(mContext).load(mFlickrResponse.getItems().get(mDesTextIconListPos).getMedia().getM())
                            .into(incidentSubTypeOptionHolderLeft.leftImageView);
                }
                break;

            case ITEM_TYPE_OPTION_RIGHT:
                if (isShowDetails && adapterClickPosition == position) {
                    FeedsOptionHolder feedsOptionHolderRight = (FeedsOptionHolder) holder;
                    feedsOptionHolderRight.container1.setBackground(mContext.getResources().getDrawable(R.drawable.border));
                    Picasso.with(mContext).load(mFlickrResponse.getItems().get(mDesTextIconListPos).getMedia().getM()).
                            into(feedsOptionHolderRight.rightImageView);
                } else {
                    FeedsOptionHolder feedsOptionHolderRight = (FeedsOptionHolder) holder;
                    feedsOptionHolderRight.container1.setBackground(null);
                    Picasso.with(mContext).load(mFlickrResponse.getItems().get(mDesTextIconListPos).getMedia().getM()).
                            into(feedsOptionHolderRight.rightImageView);
                }
                break;

            case ITEM_TYPE_EXPANDED:
                FeedsExpandedItemHolder feedsExpandedItemHolder = (FeedsExpandedItemHolder) holder;

                feedsExpandedItemHolder.nameTV.setPaintFlags(feedsExpandedItemHolder.nameTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                feedsExpandedItemHolder.nameTV.setText(mFlickrResponse.getItems().get(adapterClickPosition - 1).getTitle());

                String width = "", height = "";
                String description = mFlickrResponse.getItems().get(adapterClickPosition - 1).getDescription();
                Pattern pattern = Pattern.compile("(<img\\b|(?!^)\\G)[^>]*?\\b(src|width|height)=([\"']?)([^\"]*)\\3");
                Matcher matcher = pattern.matcher(description);
                while (matcher.find()) {
                    if (matcher.group(2).equalsIgnoreCase("width"))
                        width = matcher.group(4);
                    else if (matcher.group(2).equalsIgnoreCase("height"))
                        height = matcher.group(4);
                }

                feedsExpandedItemHolder.dimensionTV.setText(width + "x" + height);

                if (mFlickrResponse.getItems().get(adapterClickPosition - 1).getTags() != null && !mFlickrResponse.getItems().get(adapterClickPosition - 1).getTags().isEmpty())
                    feedsExpandedItemHolder.descriptionTV.setText(mFlickrResponse.getItems().get(adapterClickPosition - 1).getTags());
                else
                    feedsExpandedItemHolder.descriptionTV.setText("Date Taken: " + mFlickrResponse.getItems().get(adapterClickPosition - 1).getDateTaken());

                break;

            case ITEM_TYPE_FOOTER:
                FeedsFooterHolder feedsFooterHolder = (FeedsFooterHolder) holder;
                feedsFooterHolder.footerTV.setText(mFlickrResponse.getGenerator() + " " + mFlickrResponse.getModified());
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mFlickrResponse.getItems().size() + 2 + (isShowDetails ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return ITEM_TYPE_HEADER;
        if (position == getItemCount() - 1) return ITEM_TYPE_FOOTER;
        if (isShowDetails && position == showDetailsPosition) return ITEM_TYPE_EXPANDED;
        switch (position % 2) {
            case 1:
                return isShowDetails && position > showDetailsPosition ? ITEM_TYPE_OPTION_RIGHT : ITEM_TYPE_OPTION_LEFT;
            case 0:
                return isShowDetails && position > showDetailsPosition ? ITEM_TYPE_OPTION_LEFT : ITEM_TYPE_OPTION_RIGHT;
        }
        return -1;
    }

    void isClicked(int adapterPosition) {
        adapterClickPosition = adapterPosition;
        if (isShowDetails) {
            notifyItemRemoved(showDetailsPosition);
            notifyDataSetChanged();
        } else {
            showDetailsPosition = adapterPosition + 2 - (adapterPosition % 2 == 0 ? 1 : 0) - (adapterPosition % 2 != 0 && adapterPosition == mFlickrResponse.getItems().size() ? 1 : 0);
            notifyItemInserted(showDetailsPosition);
            notifyDataSetChanged();
        }
        isShowDetails = !isShowDetails;
    }
}

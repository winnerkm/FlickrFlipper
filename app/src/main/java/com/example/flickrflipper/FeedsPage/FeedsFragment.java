package com.example.flickrflipper.FeedsPage;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.flickrflipper.model.FlickrResponse;
import com.example.flickrflipper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FeedsFragment extends Fragment {

    public static String TAG = FeedsFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    private FlickrResponse mFlickrResponse;

    @Bind(R.id.recycler_view)
    RecyclerView mClaimsRecyclerView;
    private FeedsRecyclerAdapter mAdapter;

    public static FeedsFragment newInstance(FlickrResponse flickrResponse) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", flickrResponse);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mFlickrResponse = (FlickrResponse) getArguments().getSerializable(getString(R.string.data));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feeds, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new FeedsRecyclerAdapter(getActivity(), mFlickrResponse);
        mClaimsRecyclerView.setHasFixedSize(false);
        GridLayoutManager mng_layout = new GridLayoutManager(this.getActivity(), 2);
        mng_layout.setSpanSizeLookup(mSpanLookupSize);
        mClaimsRecyclerView.setLayoutManager(mng_layout);
        mClaimsRecyclerView.setAdapter(mAdapter);
    }

    private GridLayoutManager.SpanSizeLookup mSpanLookupSize = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            switch (mAdapter.getItemViewType(position)) {
                case FeedsRecyclerAdapter.ITEM_TYPE_EXPANDED:
                case FeedsRecyclerAdapter.ITEM_TYPE_HEADER:
                case FeedsRecyclerAdapter.ITEM_TYPE_FOOTER:
                    return 2;
                default:
                    return 1;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }
}

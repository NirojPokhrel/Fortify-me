package com.pokhrelniroj.ecomhackathon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Niroj Pokhrel on 8/14/2015.
 */
public class RecycleViewAdapterForMain extends RecyclerView.Adapter<RecycleViewAdapterForMain.ViewHolder> {
    private List<String> mListOfData;
    private static Context mContext;
    private String mData;
    private static RecycleItemsClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCategoryType;

        public ViewHolder(View view, ImageView deleteImg, TextView descriptions) {
            super(view);
            mCategoryType = descriptions;
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Remove the item from the list of view
                }
            });
        }
    }

    public RecycleViewAdapterForMain(List<String> data) {
        mListOfData = data;
    }

    @Override
    public RecycleViewAdapterForMain.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycle_view_item, parent, false );
        ImageView imgView = (ImageView)view.findViewById(R.id.delImage);
        TextView tvDsc = (TextView) view.findViewById(R.id.itemCategory);
        ViewHolder vh = new ViewHolder(view,imgView, tvDsc);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapterForMain.ViewHolder viewHolder, int position) {

        viewHolder.mCategoryType.setText(mListOfData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListOfData.size();
    }

    public interface RecycleItemsClickListener {
        public void onRecycleItemClick(int position);
    }
}

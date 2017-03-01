package com.basicflags.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.basicflags.models.Flag;
import com.basicflags.ui.views.FlagView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Unuchek (skype: kolyall) on 01.03.2017.
 */

public class FlagsAdapter extends RecyclerView.Adapter<FlagsAdapter.FlagViewHolder> {
    private ArrayList<Flag> mList = new ArrayList<>();
    @Override
    public FlagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FlagViewHolder(new FlagView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(FlagViewHolder holder, int position) {
        holder.renderView(getItem(position));
    }

    private Flag getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItems(List<Flag> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public static class FlagViewHolder extends RecyclerView.ViewHolder{

        private final FlagView mFlagView;

        public FlagViewHolder(@NonNull FlagView itemView) {
            super(itemView);
            this.mFlagView = itemView;
        }

        public void renderView(Flag item) {
            mFlagView.renderView(item);
        }
    }
}

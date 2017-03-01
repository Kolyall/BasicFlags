package com.basicflags.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.basicflags.R;
import com.basicflags.models.Flag;
import com.basicflags.module.delegate.PicassoDelegate;
import com.squareup.picasso.Picasso;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nick Unuchek (skype: kolyall) on 01.03.2017.
 */

public class FlagView extends CardView {
    private static final int VIEW_LAYOUT = R.layout.item_view_flag;
    @Nullable private Flag mItem;
    private Picasso mPicasso;
    @BindView(R.id.view_flag_image) ImageView mImageView;
    @BindView(R.id.view_flag_name) TextView mTitle;

    @BindDimen(R.dimen.image_width) int mImageWidth;
    @BindDimen(R.dimen.image_height) int mImageHeight;

    public FlagView(Context context) {
        super(context);
        setContentView(VIEW_LAYOUT);
    }

    public void setContentView(int viewLayout) {
        inflate(getContext(),viewLayout,this);
        initRootLayout();
        if (!isInEditMode()) {
            ButterKnife.bind(this);
            mPicasso = PicassoDelegate.get().getPicassoLazy().get();
        }
    }

    private void initRootLayout() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.card_view_radius);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,dimensionPixelSize,0,dimensionPixelSize);
        setLayoutParams(params);
        setRadius(dimensionPixelSize);
        setMaxCardElevation(0.5f);
        setPreventCornerOverlap(true);
        setUseCompatPadding(true);
    }

    public void renderView(Flag item) {
        this.mItem = item;
        if(mItem == null) return;
        setTitle(mItem.getName());
        setImage(item);
    }

    private void setTitle(String text) {
        mTitle.setText(text);
        mTitle.setSelected(true);
    }

    private void setImage(Flag item) {
        if (item != null &&mImageView != null && !TextUtils.isEmpty(item.getFlagLogo())) {
            String host = "https://cristiroma.github.io/countries/data/flags/";
            mPicasso.load(host+item.getFlagLogo())
                    .resize(mImageWidth,mImageHeight)
                    .placeholder(R.color.placeholder_color)
                    .into(mImageView);
        }
    }
}
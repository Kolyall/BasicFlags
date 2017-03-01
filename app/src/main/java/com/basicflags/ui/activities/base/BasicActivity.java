package com.basicflags.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.TextView;

import com.basicflags.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BasicActivity extends BaseDialogsActivity {

    @Nullable
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @Nullable @BindView(R.id.toolbar_title) TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initActionBar();
    }

    private void initActionBar() {
        if (mToolbar == null) return;
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    protected void showBackButtonOnToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_left);
        }
        if (mToolbar==null) return;
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void hideHomeAsUp() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void setTitle(int title) {
        setTitle(getString(title));
    }

    @Override
    public void setTitle(CharSequence title) {
        mToolbarTitle.setText(title);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}

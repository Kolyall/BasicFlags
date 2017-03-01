package com.basicflags.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.basicflags.R;
import com.basicflags.ui.activities.base.BasicActivity;
import com.basicflags.ui.adapters.FlagsAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

public class MainActivity extends BasicActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int VIEW_LAYOUT = R.layout.activity_main;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @Nullable private FlagsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(VIEW_LAYOUT);
        setTitle(getString(R.string.app_name));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(getAdapter());
    }

    private FlagsAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new FlagsAdapter();
        }
        return mAdapter;
    }

    @Override
    protected List<Subscription> createSubscriptions() {
        return Collections.singletonList(getFlagsSubscription());
    }

    @Override
    protected void onRetryRequest() {
        createSubscriptions();
    }

    private Subscription getFlagsSubscription() {
        return createAndAddSubscription(bindOnNextAction(getRxApiService().getFlags(),list -> getAdapter().addItems(list)));
    }

}

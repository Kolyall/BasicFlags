package com.basicflags.ui.activities.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.basicflags.R;

import java.net.UnknownHostException;

import butterknife.ButterKnife;


public abstract class BaseDialogsActivity extends BaseRxActivity {
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;
    private AlertDialog mErrorDialog;
    private AlertDialog mNetworkErrorDialog;


    protected void onRetryRequest() {}

    public void onCancelRequest() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void showAlertDialog(@StringRes int titleResId, @StringRes int messageResId) {
        String title = getResources().getString(titleResId);
        String message = getResources().getString(messageResId);
        showAlertDialog(title,message);
    }

    public void showAlertDialog(@StringRes int messageResId) {
        String message = getResources().getString(messageResId);
        showAlertDialog(null,message);
    }

    public void showAlertDialog(CharSequence message) {
        showAlertDialog(null,message);
    }

    public void showAlertDialog(CharSequence title, CharSequence message) {
        hideDialogs();
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this, R.style.AppThemeDialog)
                    .setPositiveButton(android.R.string.ok, null).create();
        }
        if (!TextUtils.isEmpty(title))
        mAlertDialog.setTitle(title);
        mAlertDialog.setMessage(message);
        mAlertDialog.show();
    }

    protected void showChooseDialog(CharSequence[] items, String title,OnItemSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(items, (dialog, item) -> {
            // Do something with the selection
            if (listener != null) {
                listener.onItemSelected(item);
            }
            dialog.dismiss();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }

    public void showAlertDialog(@StringRes int messageResId, DialogInterface.OnClickListener clickListener) {
        String message = getResources().getString(messageResId);
        showAlertDialog(message,clickListener);
    }

    public void showAlertDialog(CharSequence message, DialogInterface.OnClickListener clickListener) {
        hideDialogs();
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this, R.style.AppThemeDialog)
                    .setPositiveButton(android.R.string.ok, clickListener).create();
        }
        mAlertDialog.setMessage(message);
        mAlertDialog.show();
    }

    public void showProgressDialog(@StringRes int messageResId) {
        String message = getResources().getString(messageResId);
        showProgressDialog(message);
    }

    public void showErrorDialog(@StringRes int textId,final String errorMessage) {
        final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        onRetryRequest();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };
        DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onCancelRequest();
            }
        };
        showErrorDialog(textId, errorMessage, clickListener,onCancelListener);
    }

    public void showErrorDialog(@StringRes int textId, String errorMessage, DialogInterface.OnClickListener clickListener,
                                DialogInterface.OnCancelListener onCancelListener) {
        if (mErrorDialog == null) {
            mErrorDialog =
                    new AlertDialog.Builder(this, R.style.AppThemeDialog)
                            .setPositiveButton(textId, clickListener)
                            .setNegativeButton(R.string.cancel, clickListener)
                            .create();

            mErrorDialog.setOnCancelListener(onCancelListener);
            mErrorDialog.setMessage(errorMessage);
        }

        if (!isFinishing()&&!mErrorDialog.isShowing()) {
            mErrorDialog.show();
        }
    }

    protected void showExitDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AppThemeDialog);
        builder.setMessage(R.string.to_exit)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BaseDialogsActivity.this.finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void showProgressDialog(CharSequence message) {
        hideDialogs();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onCancelRequest();
                }
            });
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void showNonCancelableProgressDialog(@StringRes int messageResId) {
        String message = getResources().getString(messageResId);
        showNonCancelableProgressDialog(message);
    }

    public void showNonCancelableProgressDialog(CharSequence message) {
        hideDialogs();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void showNetworkErrorDialog() {
        hideDialogs();
        if (mNetworkErrorDialog == null) {
            final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            onRetryRequest();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.cancel();
                            break;
                    }
                }
            };
            mNetworkErrorDialog =
                    new AlertDialog.Builder(this, R.style.AppThemeDialog).setPositiveButton(R.string.try_again, clickListener)
                            .setNegativeButton(R.string.cancel, clickListener)
                            .create();
            final String errorMessage = getResources().getString(R.string.server_error);
            mNetworkErrorDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onCancelRequest();
                }
            });
            mNetworkErrorDialog.setMessage(errorMessage);
        }

        if (!mNetworkErrorDialog.isShowing()) {
            mNetworkErrorDialog.show();
        }
    }

    public void hideDialogs() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        if (mNetworkErrorDialog != null && mNetworkErrorDialog.isShowing()) {
            mNetworkErrorDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        hideDialogs();
        super.onPause();
    }

    @Override
    public void setTitle(int title) {
        setTitle(getString(title));
    }

    protected void clearTitle() {
        setTitle(null);
    }

    @Override
    public void doOnSubscribe() {
        showProgressDialog(R.string.loading);
    }

    @Override
    public void doOnError(Throwable throwable) {
        hideDialogs();
        throwable.printStackTrace();
        if (throwable instanceof UnknownHostException){
            showNetworkErrorDialog();
        } else {
            showNetworkErrorDialog();
            throwable.printStackTrace();
        }
    }

    @Override
    public void doOnCompleted() {
        hideDialogs();
    }



}

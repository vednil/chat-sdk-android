/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package co.chatsdk.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.DisposableMap;
import co.chatsdk.ui.activities.BaseActivity;
import co.chatsdk.ui.utils.ToastHelper;
import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by itzik on 6/17/2014.
 */
public abstract class BaseFragment extends DialogFragment implements Consumer<Throwable>, CompletableObserver {

    protected ProgressDialog progressDialog;

    protected View rootView;
    protected boolean tabIsVisible;
    protected DisposableMap dm = new DisposableMap();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setupTouchUIToDismissKeyboard(View view, final Integer... exceptIDs) {
        BaseActivity.setupTouchUIToDismissKeyboard(view, (v, event) -> {
            hideKeyboard();
            return false;
        }, exceptIDs);
    }

    protected abstract @LayoutRes int getLayout();

    public void hideKeyboard () {
        BaseActivity.hideKeyboard(getActivity());
    }

    protected void showOrUpdateProgressDialog(String message) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getActivity());

        if (!progressDialog.isShowing())
        {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(message);
            progressDialog.show();
        }
        else {
            progressDialog.setMessage(message);
        }
    }

    protected abstract void initViews();

    public void setTabVisibility (boolean isVisible) {
        tabIsVisible = isVisible;
    }

    protected void dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            ChatSDK.logError(e);
            // For handling orientation changed.
        }
    }

    abstract public void clearData ();
    public void safeReloadData () {
        if(getView() != null && ChatSDK.auth().isAuthenticated()) {
            reloadData();
        }
    }
    public abstract void reloadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dm.dispose();
    }

    @Deprecated
    protected Consumer<? super Throwable> toastOnErrorConsumer() {
        return this;
    }

    public void onSubscribe(@NonNull Disposable d) {
        dm.add(d);
    }

    /**
     * Called once the deferred computation completes normally.
     */
    public void onComplete() {

    }

    /**
     * Called once if the deferred computation 'throws' an exception.
     * @param e the exception, not null.
     */
    public void onError(@NonNull Throwable e) {
        ToastHelper.show(getContext(), e.getLocalizedMessage());
    }

    public void accept(Throwable t) {
        onError(t);
    }

}



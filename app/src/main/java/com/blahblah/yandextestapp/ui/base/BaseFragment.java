package com.blahblah.yandextestapp.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blahblah.yandextestapp.utils.KeyboardVisibilityTracker;
import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment implements KeyboardVisibilityTracker.OnKeyboardVisibilityListener {

    protected final String TAG = BaseFragment.class.getSimpleName();

    private KeyboardVisibilityTracker keyboardVisibilityTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyboardVisibilityTracker = new KeyboardVisibilityTracker(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        onViewInflated(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        keyboardVisibilityTracker.startTracking();
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardVisibilityTracker.stopTracking();
    }

    @Override
    public void onSoftInputVisibilityChanged(boolean state) {

    }

    protected KeyboardVisibilityTracker getKeyboardVisibilityTracker() {
        return keyboardVisibilityTracker;
    }

    protected void hideKeyBoard() {
        getKeyboardVisibilityTracker().hideKeyBoard(getActivity());
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onViewInflated(@NonNull final View view);

}

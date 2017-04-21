package com.blahblah.yandextestapp.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.blahblah.yandextestapp.utils.KeyboardVisibilityTracker;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements KeyboardVisibilityTracker.OnKeyboardVisibilityListener {

    protected final String TAG = BaseActivity.class.getSimpleName();

    private KeyboardVisibilityTracker keyboardVisibilityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        keyboardVisibilityTracker = new KeyboardVisibilityTracker(this, this);

        int layoutId = getLayoutId();
        if (layoutId != View.NO_ID) {
            setContentView(layoutId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        keyboardVisibilityTracker.startTracking();
        adjustRootViewHeight();
    }

    @Override
    public void onSoftInputVisibilityChanged(boolean state) {
        adjustRootViewHeight();
    }

    protected void adjustRootViewHeight() {
        View contentView = findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        params.height = keyboardVisibilityTracker.isKeyBoardOpened() ? keyboardVisibilityTracker.getUsableHeight() + getStatusBarHeight() : ViewGroup.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(params);
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void hideKeyBoard() {
        keyboardVisibilityTracker.hideKeyBoard(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        keyboardVisibilityTracker.stopTracking();
    }

    @LayoutRes
    protected abstract int getLayoutId();

}

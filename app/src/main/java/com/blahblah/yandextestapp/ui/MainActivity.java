package com.blahblah.yandextestapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.di.components.ActivityMainComponent;
import com.blahblah.yandextestapp.di.components.DaggerActivityMainComponent;
import com.blahblah.yandextestapp.di.modules.ContextModule;
import com.blahblah.yandextestapp.di.modules.DataModule;
import com.blahblah.yandextestapp.di.modules.NetworkModule;
import com.blahblah.yandextestapp.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    ApiProvider apiProvider;

    private TextView mTextMessage;
    private ScrimInsetsFrameLayout contentLayout;

    private ActivityMainComponent component;

    public ActivityMainComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityMainComponent.builder()
                .contextModule(new ContextModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .build();

        component.inject(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        contentLayout = (ScrimInsetsFrameLayout)findViewById(R.id.content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_translate:
                mTextMessage.setText(R.string.title_translate);
                return true;
            case R.id.navigation_history:
                mTextMessage.setText(R.string.title_history);
                return true;
            case R.id.navigation_settings:
                mTextMessage.setText(R.string.title_settings);
                return true;
        }
        return false;
    }
}

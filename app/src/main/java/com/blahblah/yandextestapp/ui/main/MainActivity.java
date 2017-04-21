package com.blahblah.yandextestapp.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.di.components.ActivityMainComponent;
import com.blahblah.yandextestapp.di.components.DaggerActivityMainComponent;
import com.blahblah.yandextestapp.di.modules.ContextModule;
import com.blahblah.yandextestapp.di.modules.DataModule;
import com.blahblah.yandextestapp.di.modules.MainActivityModule;
import com.blahblah.yandextestapp.di.modules.NetworkModule;
import com.blahblah.yandextestapp.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainRouter mainRouter;

    @Inject
    ApiProvider apiProvider;

    private ActivityMainComponent component;

    public ActivityMainComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        component = DaggerActivityMainComponent.builder()
                .contextModule(new ContextModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .mainActivityModule(new MainActivityModule())
                .build();

        component.inject(this);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        mainRouter.openTranslationFragment();
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
        hideKeyBoard();
        switch (item.getItemId()) {
            case R.id.navigation_translate:
                mainRouter.openTranslationFragment();
                return true;
            case R.id.navigation_history:
                mainRouter.openHistoryFragment();
                return true;
        }
        return false;
    }
}

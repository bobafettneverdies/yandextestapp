package com.blahblah.yandextestapp.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        component = DaggerActivityMainComponent.builder()
                .contextModule(new ContextModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .mainActivityModule(new MainActivityModule())
                .build();

        component.inject(this);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        removeTextLabelsAndCenterItems(navigationView);

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

    private void removeTextLabelsAndCenterItems(@NonNull BottomNavigationView bottomNavigationView) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            View view = menu.getItem(i).getActionView();

            if (view instanceof MenuView.ItemView) {
                ViewGroup viewGroup = (ViewGroup) view;
                int padding = 0;
                for (int j = 0; j < viewGroup.getChildCount(); j++) {
                    View v = viewGroup.getChildAt(j);
                    if (v instanceof ViewGroup) {
                        padding = v.getHeight();
                        viewGroup.removeViewAt(j);
                    }
                }
                viewGroup.setPadding(view.getPaddingLeft(), (viewGroup.getPaddingTop() + padding) / 2, view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }
}

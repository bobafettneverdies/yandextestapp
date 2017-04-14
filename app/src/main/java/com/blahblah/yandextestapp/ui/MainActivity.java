package com.blahblah.yandextestapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.blahblah.yandextestapp.BuildConfig;
import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextMessage;
    private ScrimInsetsFrameLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputStream inputStream = null;

        try {
            inputStream = getAssets().open(BuildConfig.YANDEX_API_KEY_FILE_NAME);
            Scanner scanner = new Scanner(inputStream);
            String apiKey = scanner.hasNext() ? scanner.next() : null;
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onCreate: " + apiKey);
        } catch (IOException e) {
            Log.e(TAG, "Problem with opening " + BuildConfig.YANDEX_API_KEY_FILE_NAME);
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e(TAG, String.format("If u want to re-use this project code u need to add file %s with your Yandex Translate API key to assets folder", BuildConfig.YANDEX_API_KEY_FILE_NAME));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Problem with closing " + BuildConfig.YANDEX_API_KEY_FILE_NAME);
                    e.printStackTrace();
                }
            }
        }

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

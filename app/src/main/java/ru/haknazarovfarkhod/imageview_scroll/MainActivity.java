package ru.haknazarovfarkhod.imageview_scroll;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.haknazarovfarkhod.imageview_scroll.utils.HttpHelper;
import ru.haknazarovfarkhod.imageview_scroll.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity
        implements BannerFragment.OnFragmentInteractionListener, ScrollFragment.OnFragmentInteractionListener {

    public static final String GLOBAL_PREFS = "global_prefs";
    public static CharSequence android_id;
    private List imageList;
    public static final String BANNER_FRAGMENT_TAG = "banner_fragment";
    public static final String SCROLLER_FRAGMENT_TAG = "scroller_fragment";
    public static final String RUN_BANNER_KEY = "run_banner";
    public static Integer ImageListPart = 0;
    public static boolean networkOK;
    public static boolean runBanner;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        SharedPreferences pref = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
        runBanner = pref.getBoolean(RUN_BANNER_KEY, true);

        networkOK = NetworkHelper.hasNetworkAccess(this);

        BannerFragment.checkBannerProp();

        ScrollFragment scrollerFragment = new ScrollFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, scrollerFragment, SCROLLER_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onButtonClick(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager == null) {
            return;
        }

        android.support.v4.app.Fragment bf = fragmentManager.findFragmentByTag(MainActivity.BANNER_FRAGMENT_TAG);

        fragmentManager
                .beginTransaction()
                .remove(bf)
                .commit();

        ScrollFragment scrollerFragment = new ScrollFragment();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, scrollerFragment, MainActivity.SCROLLER_FRAGMENT_TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.GLOBAL_PREFS, MODE_PRIVATE).edit();
        editor.putBoolean(RUN_BANNER_KEY, runBanner);
        editor.commit();
    }
}


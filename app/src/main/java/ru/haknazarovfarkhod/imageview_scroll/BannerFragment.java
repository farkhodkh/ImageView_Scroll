package ru.haknazarovfarkhod.imageview_scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import ru.haknazarovfarkhod.imageview_scroll.utils.HttpHelper;

public class BannerFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private TextView output;

    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance(String param1, String param2) {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_banner, container, false);

        output = (TextView) view.findViewById(R.id.banner_textview);

        output.append("Состояние сети: " + MainActivity.networkOK + "\n");

        MyAsyncTask task = new MyAsyncTask();
        task.execute();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class MyAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            String urlAdress = "http://newapp555.ru/farkhodfiles/test.xml";
            String result;

            try {
                result = HttpHelper.downloadUrl(urlAdress);
            } catch (IOException e) {

                e.printStackTrace();
                return null;
            }
            publishProgress(result);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            output.append(values[0] + "\n");
        }
    }

    public static boolean checkBannerProp(){
        BannerCheckAsyncTask bannerCheckAsyncTask = new BannerCheckAsyncTask();
        bannerCheckAsyncTask.execute();
        return false;
    }

    private static class BannerCheckAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            if(MainActivity.networkOK&&MainActivity.runBanner){

                String urlAdress = "http://newapp555.ru/farkhodfiles/deviceList.xml";
                String result;

                try {
                    result = HttpHelper.downloadUrl(urlAdress);
                    publishProgress(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String result = values[0];

            if (result.contains(MainActivity.android_id)){
                MainActivity.runBanner = false;
            }
        }
    }
}

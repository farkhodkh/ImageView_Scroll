package ru.haknazarovfarkhod.imageview_scroll;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static ru.haknazarovfarkhod.imageview_scroll.MainActivity.BANNER_FRAGMENT_TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScrollFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScrollFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScrollFragment extends Fragment {
    public static Integer ImageListPart = 0;


    private OnFragmentInteractionListener mListener;
    private ScrollView scrollView;

    private LinearLayout linearLayout;

    public ScrollFragment() {
        // Required empty public constructor
    }

    public static ScrollFragment newInstance(String param1, String param2) {
        ScrollFragment fragment = new ScrollFragment();
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
        final Context context = getContext();

        View view = inflater.inflate(R.layout.fragment_scroll, container, false);

        linearLayout = new LinearLayout(context);

        scrollView = view.findViewById(R.id.main_activity_scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView != null) {
                    if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())) {
                        //scroll view is at bottom
                        onScrollChangedListener(scrollView);
                    } else {
                        //scroll view is not at bottom
                    }
                }
            }
        });

        fillLinearLayout(linearLayout);

        scrollView.addView(linearLayout);


        return view;
    }

    private void fillLinearLayout(LinearLayout linearLayout) {
        Context context = getContext();
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linLayoutParam);

        Map<Integer, String> birdsImageAndDescription = getImageList();

        Iterator<Integer> birdsImageIter = birdsImageAndDescription.keySet().iterator();//установите

        while (birdsImageIter.hasNext()) {
            Integer imageResource = birdsImageIter.next();
            String description = birdsImageAndDescription.get(imageResource);

            ImageView imageView = new ImageView(context);

            imageView.setImageResource(imageResource);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(imageView);

            TextView textView = new TextView(context);
            textView.setText(description);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }

    }

    private void onScrollChangedListener(ScrollView scrollView) {

        if (!MainActivity.runBanner) {
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager == null) {
            return;
        }

        android.support.v4.app.Fragment scrollFragment = fragmentManager.findFragmentByTag(MainActivity.SCROLLER_FRAGMENT_TAG);
        if (scrollFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(scrollFragment)
                    .commit();
        }

        BannerFragment bannerFragment = new BannerFragment();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, bannerFragment, MainActivity.BANNER_FRAGMENT_TAG)
                .commit();
    }

    public Map<Integer, String> getImageList() {
        HashMap<Integer, String> birdsImageAndDescription = new HashMap<>();
        if (MainActivity.runBanner) {
            if (ImageListPart == 0) {
                birdsImageAndDescription.put(R.drawable.asian_trogon, getString(R.string.asian_trogon).toString());
                birdsImageAndDescription.put(R.drawable.australian_pied_avocet, getString(R.string.australian_pied_avocet).toString());
                birdsImageAndDescription.put(R.drawable.california_crested_quail, getString(R.string.california_crested_quail).toString());
                ImageListPart++;
            } else if (ImageListPart == 1) {
                birdsImageAndDescription.put(R.drawable.crowned_partridge, getString(R.string.crowned_partridge).toString());
                birdsImageAndDescription.put(R.drawable.golden_wren, getString(R.string.golden_wren).toString());
                birdsImageAndDescription.put(R.drawable.green_hornbeak, getString(R.string.green_hornbeak).toString());
                ImageListPart++;
            } else if (ImageListPart == 2) {
                birdsImageAndDescription.put(R.drawable.grouse, getString(R.string.grouse).toString());
                birdsImageAndDescription.put(R.drawable.lovebird, getString(R.string.lovebird).toString());
                birdsImageAndDescription.put(R.drawable.pelican, getString(R.string.pelican).toString());
                ImageListPart++;
            } else if (ImageListPart == 3) {
                birdsImageAndDescription.put(R.drawable.pheasant, getString(R.string.pheasant).toString());
                birdsImageAndDescription.put(R.drawable.white_stork, getString(R.string.white_stork).toString());
                ImageListPart = 0;
            }
        }else {
            birdsImageAndDescription.put(R.drawable.asian_trogon, getString(R.string.asian_trogon).toString());
            birdsImageAndDescription.put(R.drawable.australian_pied_avocet, getString(R.string.australian_pied_avocet).toString());
            birdsImageAndDescription.put(R.drawable.california_crested_quail, getString(R.string.california_crested_quail).toString());
            birdsImageAndDescription.put(R.drawable.crowned_partridge, getString(R.string.crowned_partridge).toString());
            birdsImageAndDescription.put(R.drawable.golden_wren, getString(R.string.golden_wren).toString());
            birdsImageAndDescription.put(R.drawable.green_hornbeak, getString(R.string.green_hornbeak).toString());
            birdsImageAndDescription.put(R.drawable.grouse, getString(R.string.grouse).toString());
            birdsImageAndDescription.put(R.drawable.lovebird, getString(R.string.lovebird).toString());
            birdsImageAndDescription.put(R.drawable.pelican, getString(R.string.pelican).toString());
            birdsImageAndDescription.put(R.drawable.pheasant, getString(R.string.pheasant).toString());
            birdsImageAndDescription.put(R.drawable.white_stork, getString(R.string.white_stork).toString());
        }
        return birdsImageAndDescription;
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
}

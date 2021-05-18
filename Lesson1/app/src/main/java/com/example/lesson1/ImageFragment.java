package com.example.lesson1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private static final int DEFAULT_INDEX = 0;
    private int index =DEFAULT_INDEX;

    private static final int[] CITY_IMAGS = {
            R.drawable.moskow,
            R.drawable.piter,
            R.drawable.ekaterenburg,
            R.drawable.novosibirsk,
            R.drawable.samara,
            R.drawable.tyumenj,
            R.drawable.novij_urengoj,
    };

    public ImageFragment() {
    }

    public static ImageFragment newInstance(int index) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX, DEFAULT_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView= view.findViewById(R.id.city_inage);

        imageView.setImageResource(CITY_IMAGS[index]);
        return view;
    }
}
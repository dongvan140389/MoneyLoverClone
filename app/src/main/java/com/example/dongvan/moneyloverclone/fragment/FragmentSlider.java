package com.example.dongvan.moneyloverclone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dongvan.moneyloverclone.R;

/**
 * Created by VoNga on 12-May-17.
 */

public class FragmentSlider extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentslider,container,false);
        Bundle bundle = getArguments();
        int linkhinh = bundle.getInt("linkhinh");

        ImageView imageView = (ImageView) view.findViewById(R.id.imgHinhSilder);

        imageView.setImageResource(linkhinh);

        return view;
    }
}

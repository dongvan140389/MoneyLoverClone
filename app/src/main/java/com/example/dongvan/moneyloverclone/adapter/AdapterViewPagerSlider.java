package com.example.dongvan.moneyloverclone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by VoNga on 12-May-17.
 */

public class AdapterViewPagerSlider extends FragmentStatePagerAdapter {
    List<Fragment> fragments;

    public AdapterViewPagerSlider(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

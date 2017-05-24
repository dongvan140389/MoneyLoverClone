package com.example.dongvan.moneyloverclone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dongvan.moneyloverclone.fragment.DangKyFragment;
import com.example.dongvan.moneyloverclone.fragment.DangNhapFragment;

/**
 * Created by VoNga on 12-May-17.
 */

public class AdapterViewPagerDangNhap extends FragmentPagerAdapter {

    public AdapterViewPagerDangNhap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                DangNhapFragment fragmentDangNhap = new DangNhapFragment();
                return fragmentDangNhap;
            case 1 :
                DangKyFragment fragmentDangKy = new DangKyFragment();
                return fragmentDangKy;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Đăng nhập";
            case 1 :
                return "Đăng ký";

            default: return null;
        }

    }
}

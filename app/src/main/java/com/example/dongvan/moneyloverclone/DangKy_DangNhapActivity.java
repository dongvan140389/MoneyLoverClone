package com.example.dongvan.moneyloverclone;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dongvan.moneyloverclone.adapter.AdapterViewPagerDangNhap;

public class DangKy_DangNhapActivity extends AppCompatActivity {

    public static String KEY_TRANDATE = "transdate";
    public static String KEY_TRANTYPE = "transtype";
    public static String KEY_TRANNAME = "transname";
    public static String KEY_ACCOUNTID = "accountid";
    public static String KEY_UEMAIL = "uemail";
    public static String KEY_AMOUNT = "amount";
    public static String TABLE_NAME = "tbTransaction";
    public static String U_EMAIL;

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky__dang_nhap);

        tabLayout = (TabLayout) findViewById(R.id.tabDangNhap);
        viewPager = (ViewPager) findViewById(R.id.viewPagerDangNhap);
        toolbar = (Toolbar) findViewById(R.id.toolBarDangNhap);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AdapterViewPagerDangNhap viewPagerAdaterDangNhap = new AdapterViewPagerDangNhap(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdaterDangNhap);
        viewPagerAdaterDangNhap.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
    }
}

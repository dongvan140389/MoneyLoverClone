package com.example.dongvan.moneyloverclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongvan.moneyloverclone.adapter.AdapterViewPagerSlider;
import com.example.dongvan.moneyloverclone.fragment.FragmentSlider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AfterSplashActivity extends AppCompatActivity{
    public static String DATABASE_NAME = "dbMoneyLove.sqlite";
    public static String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    ViewPager viewPager;
    CircleIndicator indicator;
    List<Fragment> fragmentList;

    LinearLayout btnDangNhapFace, btnDangNhapGoogle, btnDangky;
    TextView txtFace, txtGoogle, txtDangky;

    Handler autoShow;
    int currentPage = 0;

    private static final int[] IMAGES= {R.drawable.hinh1,R.drawable.hinh2,R.drawable.hinh3,R.drawable.hinh4,R.drawable.hinh5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash);
        processCopy();
        addControls();
        addEvents();
        showSlide(IMAGES);
        autoShow = new Handler();

    }

    private void addEvents() {
        btnDangNhapFace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    btnDangNhapFace.setBackground(getResources().getDrawable(R.drawable.buttonfacerelease));
                    txtFace.setTextColor(Color.parseColor("#FFFFFF"));

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    btnDangNhapFace.setBackground(getResources().getDrawable(R.drawable.buttonfacepress));
                    txtFace.setTextColor(Color.parseColor("#0078d4"));
                }
                return true;
            }
        });

        btnDangNhapGoogle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    btnDangNhapGoogle.setBackground(getResources().getDrawable(R.drawable.buttongooglepress));
                    txtGoogle.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    btnDangNhapGoogle.setBackground(getResources().getDrawable(R.drawable.buttongooglerelease));
                    txtGoogle.setTextColor(Color.parseColor("#ff2e1f"));
                }
                return true;
            }
        });

        btnDangky.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    btnDangky.setBackground(getResources().getDrawable(R.drawable.buttonminepress));
                    txtDangky.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    btnDangky.setBackground(getResources().getDrawable(R.drawable.buttonminerelease));
                    txtDangky.setTextColor(Color.parseColor("#2baf2b"));
                    Intent intent = new Intent(AfterSplashActivity.this,DangKy_DangNhapActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    private void addControls() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_custom);
        indicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        btnDangky = (LinearLayout) findViewById(R.id.btnDangKy);
        btnDangNhapFace = (LinearLayout) findViewById(R.id.btnDangNhapFace);
        btnDangNhapGoogle = (LinearLayout) findViewById(R.id.btnDangNhapGoogle);
        txtDangky = (TextView) findViewById(R.id.txtDangky);
        txtFace = (TextView) findViewById(R.id.txtFace);
        txtGoogle = (TextView) findViewById(R.id.txtGoogle);
    }

    private void autoSlider(){
        autoShow.postDelayed(runableShow,3000);
    }

    private Runnable runableShow = new Runnable() {
        @Override
        public void run() {
            currentPage++;

            viewPager.setCurrentItem(currentPage,true);
            if(currentPage<IMAGES.length){
                autoSlider();
            }
        }
    };

    public void showSlide(int[] linkhinh) {
        fragmentList = new ArrayList<>();

        for (int i=0;i<linkhinh.length ;i++){
            FragmentSlider fragment = new FragmentSlider();
            Bundle bundle = new Bundle();
            bundle.putInt("linkhinh",linkhinh[i]);
            fragment.setArguments(bundle);

            fragmentList.add(fragment);

        }

        AdapterViewPagerSlider adapterViewPagerSlider = new AdapterViewPagerSlider(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapterViewPagerSlider);
        adapterViewPagerSlider.notifyDataSetChanged();
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoSlider();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoShow.removeCallbacks(runableShow);
    }

    //Show 1 dialog khi bấm nút back trên điện thoại
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn muốn thoát ứng dụng không?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    //Code copy database sqlite vào app
    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;

            myInput = getAssets().open(DATABASE_NAME);


            // Path to the just created empty db
            String outFileName = getDatabasePath();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

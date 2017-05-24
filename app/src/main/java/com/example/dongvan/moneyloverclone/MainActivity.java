package com.example.dongvan.moneyloverclone;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.dongvan.moneyloverclone.adapter.AdapterViewPagerMain;
import com.example.dongvan.moneyloverclone.fragment.FragmentMain;
import com.example.dongvan.moneyloverclone.model.TypeAccount;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import static com.example.dongvan.moneyloverclone.AfterSplashActivity.database;

public class MainActivity extends AppCompatActivity {

    public static int ACCOUNT_ID;

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    List<Fragment> fragmentList;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private List<TypeAccount> listAccount;
    private List<IProfile> listProfile;
    //private IProfile lastProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        getAccountList();
        createDrawer();
        toolbar.setTitle("Tài khoản ngân hàng");
        showFragmentMain();
    }

    public void showFragmentMain(){
        fragmentList = new ArrayList<>();
        FragmentMain fragment = new FragmentMain();
        fragmentList.add(fragment);

        AdapterViewPagerMain adapterMain = new AdapterViewPagerMain(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapterMain);
        adapterMain.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);

    }

    private void addControls() {
        tabLayout = (TabLayout) findViewById(R.id.tabMain);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
        toolbar = (Toolbar) findViewById(R.id.toolBarMain);
        setSupportActionBar(toolbar);
    }

    private List<TypeAccount> getAccountList(){
        listAccount = new ArrayList<>();
        database = openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbAccount",null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            TypeAccount acc = new TypeAccount(cursor.getInt(0), cursor.getString(1));
            listAccount.add(acc);
            cursor.moveToNext();
        }
        return listAccount;
    }

    private void createDrawer(){
        // Create a few sample profile
        listProfile = new ArrayList<>();
        IProfile profile = null;
        for(int i=0;i<listAccount.size();i++){
            if(listAccount.get(i).getAccountID()==2) {
                profile = new ProfileDrawerItem().withName("Tài khoản hiện tại")
                        .withEmail(listAccount.get(i).getName()).withIcon(R.drawable.bank).withIdentifier(listAccount.get(i).getAccountID());
            }
            if(listAccount.get(i).getAccountID()==1){
                profile = new ProfileDrawerItem().withName("Tài khoản hiện tại")
                        .withEmail(listAccount.get(i).getName()).withIcon(R.drawable.bank).withIdentifier(listAccount.get(i).getAccountID());
            }else{
                profile = new ProfileDrawerItem().withName("Tài khoản hiện tại")
                        .withEmail(listAccount.get(i).getName()).withIcon(R.drawable.defaultaccount).withIdentifier(i+1);
            }
            listProfile.add(profile);
        }

//        final IProfile profile = new ProfileDrawerItem().withName("Vô Ngã TS")
//                .withEmail("dongvan140389@gmail.com").withIcon(getResources()
//                        .getDrawable(R.drawable.hinhthe)).withIdentifier(100);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Sổ giao dịch").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1);//withSelectable(false);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Sổ nợ").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(2);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Xu hướng").withDescription("Những phong cách mới").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3);
        SectionDrawerItem item4 = new SectionDrawerItem().withName("Tính năng nâng cao");
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIcon(FontAwesome.Icon.faw_cog).withIdentifier(5).withName("Liên kết ngân hàng");
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withName("Ngân sách").withIcon(FontAwesome.Icon.faw_github).withIdentifier(7);
        SecondaryDrawerItem item8 = new SecondaryDrawerItem().withName("Tiết kiệm").withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withIdentifier(8);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .withProfiles(listProfile)
                .addProfiles(
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Thêm tài khoản").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(100000),
                        new ProfileSettingDrawerItem().withName("Quản lý tài khoản").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withSelectionFirstLineShown(true)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        //lastProfile = profile;
                        toolbar.setTitle(profile.getEmail().toString());
                        ACCOUNT_ID = (int)profile.getIdentifier();


                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
//                        if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
//                            int count = 100 + headerResult.getProfiles().size() + 1;
//                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Tài khoản" + count).withEmail("taikhoan" + count + "@gmail.com").withIcon(R.drawable.profile5).withIdentifier(count);
//                            if (headerResult.getProfiles() != null) {
//                                //we know that there are 2 setting elements. set the new profile above them ;)
//                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
//                            } else {
//                                headerResult.addProfiles(newProfile);
//                            }
//                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .build();
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        item5,
                        item7,
                        item8
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem!=null){
                            Intent intent = null;
                            switch ((int)drawerItem.getIdentifier()){
                                case 1:
                                    //intent = new Intent(MainActivity.this,CompactHeaderDrawerActivity.class);
                                    break;
                                case 2:
                                    //intent = new Intent(MainActivity.this,ActionBarActivity.class);
                                    break;
                            }

                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                }).build();

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

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "Trạng thái Resume Main", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "Trạng thái Pause Main", Toast.LENGTH_SHORT).show();
    }
}
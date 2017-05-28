package com.example.dongvan.moneyloverclone.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dongvan.moneyloverclone.AfterSplashActivity;
import com.example.dongvan.moneyloverclone.MainActivity;
import com.example.dongvan.moneyloverclone.R;
import com.example.dongvan.moneyloverclone.model.User;

import static com.example.dongvan.moneyloverclone.AfterSplashActivity.database;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.U_EMAIL;

/**
 * Created by VoNga on 12-May-17.
 */

public class DangNhapFragment extends Fragment {

    LinearLayout lnLayout;
    EditText edTenDangNhap,edMatKhau;
    Button btnDangNhap;
    String uemail,upass;
    User u;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_nhap,container,false);
        addControls(view);

        edTenDangNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edTenDangNhap.getText().toString().trim().equals("")){
                    edTenDangNhap.setError("Email đăng nhập không được bỏ trống");
                    edTenDangNhap.requestFocus();
                }else{
                    edTenDangNhap.setError(null);
                }
            }
        });

        edMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edMatKhau.getText().toString().trim().equals("")){
                    edMatKhau.setError("Mật khẩu không được bỏ trống");
                    edMatKhau.requestFocus();
                }else{
                    edMatKhau.setError(null);
                }
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInput()){
                    return;
                }else{
                    uemail = edTenDangNhap.getText().toString().trim();
                    upass = edMatKhau.getText().toString().trim();
                    if(checkLogin(uemail,upass)){
                        U_EMAIL = uemail;
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Snackbar snackbar = Snackbar
                                .make(lnLayout, "Email hoặc mật khẩu không đúng", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
            }
        });
        return view;

    }

    private void addControls(View view) {
        lnLayout = (LinearLayout) view.findViewById(R.id.lnLayout);
        btnDangNhap = (Button) view.findViewById(R.id.btnDangNhap);
        edTenDangNhap = (EditText) view.findViewById(R.id.edDiaChiEmailDangNhap);
        edMatKhau = (EditText) view.findViewById(R.id.edMatKhauDangNhap);

    }

    private boolean validateInput(){
        if(edTenDangNhap.getText().toString().trim().equals("")){
            edTenDangNhap.setError("Email đăng nhập không được bỏ trống");
            edTenDangNhap.requestFocus();
            return false;
        }
        if(edMatKhau.getText().toString().trim().equals("")){
            edMatKhau.setError("Mật khẩu không được bỏ trống");
            edMatKhau.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkLogin(String email, String pass){
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbUser WHERE uemail = ? AND upassword = ?",new String[] {email,pass});

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            u = new User(cursor.getString(0),cursor.getString(1));
            return true;
        }
        return false;
    }

}

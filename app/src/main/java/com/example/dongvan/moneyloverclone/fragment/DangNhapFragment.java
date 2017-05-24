package com.example.dongvan.moneyloverclone.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    EditText edTenDangNhap,edMatKhau;
    Button btnDangNhap;
    String uemail,upass;
    User u;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_nhap,container,false);
        addControls(view);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uemail = edTenDangNhap.getText().toString().trim();
                upass = edMatKhau.getText().toString().trim();
                if(checkLogin(uemail,upass)){
                    Toast.makeText(getActivity(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    U_EMAIL = uemail;
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;

    }

    private void addControls(View view) {
        btnDangNhap = (Button) view.findViewById(R.id.btnDangNhap);
        edTenDangNhap = (EditText) view.findViewById(R.id.edDiaChiEmailDangNhap);
        edMatKhau = (EditText) view.findViewById(R.id.edMatKhauDangNhap);
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

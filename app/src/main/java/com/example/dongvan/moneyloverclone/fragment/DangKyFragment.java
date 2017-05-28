package com.example.dongvan.moneyloverclone.fragment;

import android.content.ContentValues;
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

public class DangKyFragment extends Fragment {
    LinearLayout lnLayoutDK;
    EditText edDiaChiEmailDK, edMatKhauDK, edNhapLaiMatKhauDK;
    Button btnDangKy;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dang_ky, container, false);
        return view;
    }

    private void addControls(View view) {
        lnLayoutDK = (LinearLayout) view.findViewById(R.id.lnLayoutDK);
        btnDangKy = (Button) view.findViewById(R.id.btnDangKy);
        edDiaChiEmailDK = (EditText) view.findViewById(R.id.edDiaChiEmailDK);
        edMatKhauDK = (EditText) view.findViewById(R.id.edMatKhauDK);
        edNhapLaiMatKhauDK = (EditText) view.findViewById(R.id.edNhapLaiMatKhauDK);
    }

    private boolean validateInput() {
        if (edDiaChiEmailDK.getText().toString().trim().equals("")) {
            edDiaChiEmailDK.setError("Email đăng ký không được bỏ trống");
            edDiaChiEmailDK.requestFocus();
            return false;
        }
        if (edMatKhauDK.getText().toString().trim().equals("")) {
            edMatKhauDK.setError("Mật khẩu không được bỏ trống");
            edMatKhauDK.requestFocus();
            return false;
        }
        if (edNhapLaiMatKhauDK.getText().toString().trim().equals("")) {
            edNhapLaiMatKhauDK.setError("Nhập lại Mật khẩu không được bỏ trống");
            edNhapLaiMatKhauDK.requestFocus();
            return false;
        }

        if (!edNhapLaiMatKhauDK.getText().toString().trim().equals(edMatKhauDK.getText().toString().trim())) {
            edNhapLaiMatKhauDK.setError("Nhập lại mật khẩu không đúng");
            edNhapLaiMatKhauDK.requestFocus();
            return false;
        }

        return true;
    }

    private boolean registerUser(User u){
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbUser WHERE uemail = ?",new String[] {u.getEmail()});

        if(cursor.getCount()>0){
            return false;
        }else{
            ContentValues values = new ContentValues();
            values.put("uemail", u.getEmail());
            values.put("upassword", u.getPassword());

            database.insert("tbUser", null, values);
            database.close();
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        addControls(view);

        edDiaChiEmailDK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edDiaChiEmailDK.getText().toString().trim().equals("")){
                    edDiaChiEmailDK.setError("Email đăng ký không được bỏ trống");
                    edDiaChiEmailDK.requestFocus();
                }else{
                    edDiaChiEmailDK.setError(null);
                }
            }
        });
        edMatKhauDK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edMatKhauDK.getText().toString().trim().equals("")){
                    edMatKhauDK.setError("Mật khẩu không được bỏ trống");
                    edMatKhauDK.requestFocus();
                }else{
                    edMatKhauDK.setError(null);
                }
            }
        });
        edNhapLaiMatKhauDK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edNhapLaiMatKhauDK.getText().toString().trim().equals("")){
                    edNhapLaiMatKhauDK.setError("Nhập lại Mật khẩu không được bỏ trống");
                    edNhapLaiMatKhauDK.requestFocus();
                }else{
                    edNhapLaiMatKhauDK.setError(null);
                }
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInput()) {
                    return;
                } else {
                    User u = new User(edDiaChiEmailDK.getText().toString(),edMatKhauDK.getText().toString());
                    if(registerUser(u)){
                        U_EMAIL = u.getEmail();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Snackbar snackbar = Snackbar
                                .make(lnLayoutDK, "Email đã tồn tại", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
            }
        });
    }
}

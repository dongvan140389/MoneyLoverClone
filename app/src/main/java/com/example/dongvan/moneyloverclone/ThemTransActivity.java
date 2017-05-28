package com.example.dongvan.moneyloverclone;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dongvan.moneyloverclone.model.TransactionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.dongvan.moneyloverclone.AfterSplashActivity.database;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_ACCOUNTID;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_AMOUNT;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_TRANDATE;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_TRANNAME;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_TRANTYPE;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.KEY_UEMAIL;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.TABLE_NAME;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.U_EMAIL;
import static com.example.dongvan.moneyloverclone.MainActivity.ACCOUNT_ID;

public class ThemTransActivity extends AppCompatActivity {

    EditText txtAmountDialog,txtTranNameDialog;
    TextView txtDateTranDialog;
    AppCompatSpinner spinTranType;
    Button btnThemDialog,btnThoatDialog;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int spinSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_trans);
        addControls();
        addItemsOnSpinner();
        addEvents();
    }

    private void addEvents() {
        btnThoatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spinTranType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnThemDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInput()){
                    return;
                }else{
                    TransactionModel tran = new TransactionModel(txtDateTranDialog.getText().toString(),
                            spinSelect,
                            txtTranNameDialog.getText().toString(),
                            ACCOUNT_ID,
                            U_EMAIL,
                            Double.parseDouble(txtAmountDialog.getText().toString()));
                    addTran(tran);
                    Intent intent = new Intent(ThemTransActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        txtDateTranDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("Vui lòng chọn loại chi tiêu...");
        list.add("Dùng cho chi tiêu");
        list.add("Tiền thu nhập");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ThemTransActivity.this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTranType.setAdapter(dataAdapter);

    }

    private void showDatePicker() {
        final DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                txtDateTranDialog.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(ThemTransActivity.this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private boolean validateInput(){
        if(txtAmountDialog.getText().toString().equals("")){
            txtAmountDialog.setError("Khoản tiền không được bỏ trống");
            txtAmountDialog.requestFocus();
            return false;
        }
        return true;
    }

    private void addControls() {
        txtAmountDialog = (EditText) findViewById(R.id.txtAmountDialog);
        txtTranNameDialog = (EditText) findViewById(R.id.txtTranNameDialog);
        txtDateTranDialog = (TextView) findViewById(R.id.txtDateTranDialog);
        spinTranType = (AppCompatSpinner) findViewById(R.id.spinTranType);
        btnThemDialog = (Button) findViewById(R.id.btnThemDialog);
        btnThoatDialog = (Button) findViewById(R.id.btnThoatDialog);
    }

    public void addTran(TransactionModel tran) {
        database = openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);

        ContentValues values = new ContentValues();
        values.put(KEY_TRANDATE, tran.getTranDate());
        values.put(KEY_TRANTYPE, tran.getTranType());
        values.put(KEY_TRANNAME, tran.getTranName());
        values.put(KEY_ACCOUNTID, tran.getAccountID());
        values.put(KEY_UEMAIL, tran.getUemail());
        values.put(KEY_AMOUNT, tran.getTranAmount());

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

}

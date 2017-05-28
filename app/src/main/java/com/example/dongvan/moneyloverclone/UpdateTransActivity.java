package com.example.dongvan.moneyloverclone;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
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

public class UpdateTransActivity extends AppCompatActivity {

    EditText txtAmountUpdate,txtTranNameUpdate;
    TextView txtDateTranUpdate;
    AppCompatSpinner spinTranTypeUpdate;
    Button btnUpdate,btnThoatUpdate;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int spinSelect;

    int tranID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trans);
        addControls();
        addItemsOnSpinner();
        initUpdate();
        addEvents();
    }

    private void initUpdate(){
        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getBundleExtra("I_TRAN");
        TransactionModel tran = (TransactionModel) bundle.getSerializable("TRAN");
        tranID = tran.getTranID();
        txtDateTranUpdate.setText(tran.getTranDate());
        txtTranNameUpdate.setText(tran.getTranName());
        txtAmountUpdate.setText(String.valueOf(tran.getTranAmount()));
        spinTranTypeUpdate.setSelection(tran.getTranType());
    }

    private void addEvents() {
        btnThoatUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInput()){
                    return;
                }else{
                    TransactionModel tran = new TransactionModel(tranID,txtDateTranUpdate.getText().toString(),
                            spinSelect,
                            txtTranNameUpdate.getText().toString(),
                            ACCOUNT_ID,
                            U_EMAIL,
                            Double.parseDouble(txtAmountUpdate.getText().toString()));
                    updateTran(tran);
                    Intent intent = new Intent(UpdateTransActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        spinTranTypeUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinSelect = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtDateTranUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void updateTran(TransactionModel tran) {
        database = openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);

        ContentValues values = new ContentValues();
        values.put(KEY_TRANDATE, tran.getTranDate());
        values.put(KEY_TRANTYPE, tran.getTranType());
        values.put(KEY_TRANNAME, tran.getTranName());
        values.put(KEY_ACCOUNTID, tran.getAccountID());
        values.put(KEY_UEMAIL, tran.getUemail());
        values.put(KEY_AMOUNT, tran.getTranAmount());

        Log.d("abc"," => "+tran.getTranName()+" - "+tran.getTranAmount());
        database.update(TABLE_NAME, values, "transid = "+tran.getTranID(),null);
        Log.d("abc"," => "+tran.getTranName()+" - "+tran.getTranAmount());
        database.close();
    }

    private void addControls() {
        txtAmountUpdate = (EditText) findViewById(R.id.txtAmountUpdate);
        txtTranNameUpdate = (EditText) findViewById(R.id.txtTranNameUpdate);
        txtDateTranUpdate = (TextView) findViewById(R.id.txtDateTranUpdate);
        spinTranTypeUpdate = (AppCompatSpinner) findViewById(R.id.spinTranTypeUpdate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnThoatUpdate = (Button) findViewById(R.id.btnThoatUpdate);
    }

    private void showDatePicker() {
        final DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                txtDateTranUpdate.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(UpdateTransActivity.this,
                callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("Vui lòng chọn loại chi tiêu...");
        list.add("Dùng cho chi tiêu");
        list.add("Tiền thu nhập");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(UpdateTransActivity.this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTranTypeUpdate.setAdapter(dataAdapter);
    }

    private boolean validateInput(){
        if(txtAmountUpdate.getText().toString().equals("")){
            txtAmountUpdate.setError("Khoản tiền không được bỏ trống");
            txtAmountUpdate.requestFocus();
            return false;
        }
        return true;
    }
}

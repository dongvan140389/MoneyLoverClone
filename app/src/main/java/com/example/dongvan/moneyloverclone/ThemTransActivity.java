package com.example.dongvan.moneyloverclone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dongvan.moneyloverclone.fragment.FragmentMain;
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

public class ThemTransActivity extends Dialog {

    EditText txtAmountDialog,txtTranNameDialog;
    TextView txtDateTranDialog;
    AppCompatSpinner spinTranType;
    Button btnThemDialog,btnThoatDialog;
    Activity activity;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int spinSelect;

    public ThemTransActivity(Activity activity) {
        super(activity);
        this.activity = activity;
        setContentView(R.layout.activity_them_trans);
        setTitle("Thêm chi tiêu");
        addControls();
        addEvents();
        addItemsOnSpinner();
    }

    private void addEvents() {
        btnThoatDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemTransActivity.this.dismiss();
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
                    //Log.d("themTran"," => "+tran.toString());
                    addTran(tran);
                    //FragmentMain.adapterListTran.notifyDataSetChanged();
                    ThemTransActivity.this.dismiss();

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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, list);
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
        DatePickerDialog dialog = new DatePickerDialog(getContext(),
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
        database = getContext().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);

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

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        FragmentMain frag = new FragmentMain();
        frag.adapterListTran.notifyDataSetChanged();
    }
}

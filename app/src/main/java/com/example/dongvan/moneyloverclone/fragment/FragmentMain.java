package com.example.dongvan.moneyloverclone.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dongvan.moneyloverclone.AfterSplashActivity;
import com.example.dongvan.moneyloverclone.R;
import com.example.dongvan.moneyloverclone.ThemTransActivity;
import com.example.dongvan.moneyloverclone.TongQuanActivity;
import com.example.dongvan.moneyloverclone.UpdateTransActivity;
import com.example.dongvan.moneyloverclone.adapter.AdapterListTran;
import com.example.dongvan.moneyloverclone.model.TransactionModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.dongvan.moneyloverclone.AfterSplashActivity.database;
import static com.example.dongvan.moneyloverclone.DangKy_DangNhapActivity.U_EMAIL;
import static com.example.dongvan.moneyloverclone.MainActivity.ACCOUNT_ID;

/**
 * Created by VoNga on 16-May-17.
 */

public class FragmentMain  extends Fragment {

    ImageView btnTongQuan;
    TextView txtTienVao,txtTienRa,txtTongTien;
    ExpandableListView elvTrans;
    AdapterListTran adapterListTran;
    List<TransactionModel> listTranByDate;
    List<TransactionModel> listAllTran;
    List<TransactionModel> listTran;
    HashMap<String, List<TransactionModel>> mData;
    FloatingActionButton floatButton;

    List<String> listHeader;
    double tienvaoIntent= 0;
    double tienraIntent = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentmain,container,false);
        addControls(view);
        showData();
        btnTongQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TongQuanActivity.class);
                intent.putExtra("TIENRA",tienraIntent);
                intent.putExtra("TIENVAO",tienvaoIntent);
                startActivity(intent);
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemTransActivity.class);
                startActivity(intent);
            }
        });
        elvTrans.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getActivity(),UpdateTransActivity.class);
                Bundle bundle = new Bundle();
                TransactionModel tran = (TransactionModel) parent.getExpandableListAdapter().getChild(groupPosition,childPosition);
                bundle.putSerializable("TRAN",tran);
                intent.putExtra("I_TRAN",bundle);
                startActivity(intent);
                return false;
            }
        });
        return view;
    }

    private void addControls(View view) {
        btnTongQuan= (ImageView) view.findViewById(R.id.btnTongQuan);
        txtTienRa = (TextView) view.findViewById(R.id.txtTienRa);
        txtTienVao= (TextView) view.findViewById(R.id.txtTienVao);
        txtTongTien = (TextView) view.findViewById(R.id.txtTongTien);
        elvTrans = (ExpandableListView) view.findViewById(R.id.elvTrans);
        floatButton= (FloatingActionButton) view.findViewById(R.id.floatButton);
    }

    public void showData(){
        loadTranDate(U_EMAIL,ACCOUNT_ID);
        //data for child
        mData = new HashMap<>();

        for(int i=0;i<listHeader.size();i++){
            listTran = loadTranDetail(listHeader.get(i),U_EMAIL,ACCOUNT_ID);
            mData.put(listHeader.get(i), listTran);
        }

        adapterListTran = new AdapterListTran(getContext(), listHeader, mData);
        elvTrans.setAdapter(adapterListTran);
        adapterListTran.notifyDataSetChanged();

        showAmount();
    }

    private void showAmount() {
        double tienvao = 0;
        double tienra = 0;
        double tongtien = 0;

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        loadAllTran(U_EMAIL,ACCOUNT_ID);

        for(TransactionModel tran : listAllTran){
            if(tran.getTranType()==1) {
                tienra += tran.getTranAmount();
            }else{
                tienvao += tran.getTranAmount();
            }
        }
        tongtien = tienvao - tienra;
        txtTienVao.setText(currencyFormatter.format(tienvao));
        txtTienRa.setText(currencyFormatter.format(tienra));
        txtTongTien.setText(currencyFormatter.format(tongtien));
        tienraIntent = tienra;
        tienvaoIntent = tienvao;
    }

    public List<String> loadTranDate(String uemail, int accoundID){
        listHeader = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbTransaction WHERE uemail = "+"'"+uemail+"'"+ " AND accountid = "+accoundID+" GROUP BY transdate",null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            listHeader.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return listHeader;
    }

    public List<TransactionModel> loadTranDetail(String tranDate,String uemail, int accoundID){
        listTranByDate = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbTransaction WHERE transdate = '"+tranDate+"' AND uemail = "+"'"+uemail+"'"+ " AND accountid = "+accoundID,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            TransactionModel tran = new TransactionModel();
            tran.setTranID(cursor.getInt(0));
            tran.setTranDate(cursor.getString(1));
            tran.setTranType(cursor.getInt(2));
            tran.setTranName(cursor.getString(3));
            tran.setAccountID(cursor.getInt(4));
            tran.setUemail(cursor.getString(5));
            tran.setTranAmount(cursor.getDouble(6));
            listTranByDate.add(tran);
            cursor.moveToNext();
        }
        return listTranByDate;
    }

    public List<TransactionModel> loadAllTran(String uemail, int accoundID){
        listAllTran = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbTransaction WHERE uemail = "+"'"+uemail+"'"+ " AND accountid = "+accoundID,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            TransactionModel tran = new TransactionModel();
            tran.setTranID(cursor.getInt(0));
            tran.setTranDate(cursor.getString(1));
            tran.setTranType(cursor.getInt(2));
            tran.setTranName(cursor.getString(3));
            tran.setAccountID(cursor.getInt(4));
            tran.setUemail(cursor.getString(5));
            tran.setTranAmount(cursor.getDouble(6));
            listAllTran.add(tran);
            cursor.moveToNext();
        }
        return listAllTran;
    }



    @Override
    public void onResume() {
        super.onResume();
        //adapterListTran.notifyDataSetChanged();
    }

}

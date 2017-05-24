package com.example.dongvan.moneyloverclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dongvan.moneyloverclone.AfterSplashActivity;
import com.example.dongvan.moneyloverclone.R;
import com.example.dongvan.moneyloverclone.ThemTransActivity;
import com.example.dongvan.moneyloverclone.TongQuanActivity;
import com.example.dongvan.moneyloverclone.adapter.AdapterListTran;
import com.example.dongvan.moneyloverclone.model.TransactionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.dongvan.moneyloverclone.AfterSplashActivity.database;

/**
 * Created by VoNga on 16-May-17.
 */

public class FragmentMain  extends Fragment {

    ImageView btnTongQuan;
    TextView txtTienVao,txtTienRa;
    ExpandableListView elvTrans;
    public static AdapterListTran adapterListTran;
    HashMap<String, List<TransactionModel>> mData;
    FloatingActionButton floatButton;

    List<String> listHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentmain,container,false);
        addControls(view);
        btnTongQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TongQuanActivity.class);
                startActivity(intent);
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemTransActivity myDialog = new ThemTransActivity(getActivity());
                myDialog.show();
            }
        });
        showData();
        return view;
    }

    private void addControls(View view) {
        btnTongQuan= (ImageView) view.findViewById(R.id.btnTongQuan);
        txtTienRa = (TextView) view.findViewById(R.id.txtTienRa);
        txtTienVao= (TextView) view.findViewById(R.id.txtTienVao);
        elvTrans = (ExpandableListView) view.findViewById(R.id.elvTrans);
        floatButton= (FloatingActionButton) view.findViewById(R.id.floatButton);
    }

    public void showData(){
        loadTranDate();
        //data for child
        mData = new HashMap<>();

        for(int i=0;i<listHeader.size();i++){
            List<TransactionModel> listTran = loadTranDetail(listHeader.get(i));
            Log.d("setCursor"," => số lượng tran trong for "+listTran.size());
            mData.put(listHeader.get(i), listTran);
        }

        adapterListTran = new AdapterListTran(getContext(), listHeader, mData);
        elvTrans.setAdapter(adapterListTran);
    }

    public List<String> loadTranDate(){
        listHeader = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbTransaction GROUP BY transdate",null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            listHeader.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return listHeader;
    }

    public List<TransactionModel> loadTranDetail(String tranDate){
        List<TransactionModel> listTran = new ArrayList<>();
        database = getActivity().openOrCreateDatabase(AfterSplashActivity.DATABASE_NAME,android.content.Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM tbTransaction WHERE transdate = '"+tranDate+"'",null);
        Log.d("setCursor"," => số lượng tran chi tiết "+cursor.getCount());
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
            listTran.add(tran);
            cursor.moveToNext();
        }
        return listTran;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "Trạng thái Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "Trạng thái Pause", Toast.LENGTH_SHORT).show();
        //showData();
        adapterListTran.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getContext(), "Trạng thái Detach", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), "Trạng thái Attach", Toast.LENGTH_SHORT).show();
    }
}

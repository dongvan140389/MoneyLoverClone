package com.example.dongvan.moneyloverclone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dongvan.moneyloverclone.R;
import com.example.dongvan.moneyloverclone.model.TransactionModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by VoNga on 21-May-17.
 */

public class AdapterListTran extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mHeaderGroup;
    private HashMap<String, List<TransactionModel>> mDataChild;

    public AdapterListTran(Context context, List<String> headerGroup, HashMap<String, List<TransactionModel>> datas) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = datas;
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.d("fakeData", "lúc này convertview null");
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_group,null);
        }
        TextView tvHeader = (TextView) convertView.findViewById(R.id.txtDateTran);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_child,null);
        }

        TextView txtNameTran = (TextView) convertView.findViewById(R.id.txtNameTran);
        TextView txtAmountTran = (TextView) convertView.findViewById(R.id.txtAmountTran);
        txtNameTran.setText(((TransactionModel) getChild(groupPosition, childPosition)).getTranName());
        txtAmountTran.setText(String.valueOf(((TransactionModel) getChild(groupPosition, childPosition)).getTranAmount()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

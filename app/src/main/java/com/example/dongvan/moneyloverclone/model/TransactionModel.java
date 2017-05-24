package com.example.dongvan.moneyloverclone.model;

/**
 * Created by VoNga on 18-May-17.
 */

public class TransactionModel {
    private int tranID;
    private String tranDate;
    private int tranType;
    private String tranName;
    private int accountID;
    private String uemail;
    private double tranAmount;

    public TransactionModel() {
    }

    public TransactionModel(String tranDate, int tranType, String tranName, int accountID, String uemail,double tranAmount) {
        this.tranDate = tranDate;
        this.tranType = tranType;
        this.tranName = tranName;
        this.accountID = accountID;
        this.uemail = uemail;
        this.tranAmount = tranAmount;
    }

    public int getTranID() {
        return tranID;
    }

    public void setTranID(int tranID) {
        this.tranID = tranID;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    @Override
    public String toString() {
        return "tranDate:"+tranDate+" - "+"tranName:"+tranName+" - "
                +"tranType:"+tranType+" - "+"accountID:"+accountID+" - "+"UEmail:"+uemail;
    }
}

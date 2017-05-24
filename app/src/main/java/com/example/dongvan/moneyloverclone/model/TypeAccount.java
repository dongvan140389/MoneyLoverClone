package com.example.dongvan.moneyloverclone.model;

/**
 * Created by VoNga on 18-May-17.
 */

public class TypeAccount {
    private int accountID;
    private String name;

    public TypeAccount() {
    }

    public TypeAccount(int accountID, String name) {
        this.accountID = accountID;
        this.name = name;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

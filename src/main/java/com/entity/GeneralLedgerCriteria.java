package com.entity;

import com.RMT2Base;

/**
 * Selection criteria class for General Ledger Account search operations.
 * 
 * @author appdev
 *
 */
public class GeneralLedgerCriteria extends RMT2Base {
    private int accountId;
    private String accountNo;
    private String accountName;
    private String accountCode;
    private String accountDesc;
    private int balanceTypeId;
    private String balanceTypeDesc;
    private String balanceTypeShortDesc;
    private int acctTypeId;
    private String acctTypeDesc;
    private int acctCatgId;
    private String acctCatgDesc;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountDesc() {
        return accountDesc;
    }

    public void setAccountDesc(String accountDesc) {
        this.accountDesc = accountDesc;
    }

    public int getBalanceTypeId() {
        return balanceTypeId;
    }

    public void setBalanceTypeId(int balanceTypeId) {
        this.balanceTypeId = balanceTypeId;
    }

    public String getBalanceTypeDesc() {
        return balanceTypeDesc;
    }

    public void setBalanceTypeDesc(String balanceTypeDesc) {
        this.balanceTypeDesc = balanceTypeDesc;
    }

    public String getBalanceTypeShortDesc() {
        return balanceTypeShortDesc;
    }

    public void setBalanceTypeShortDesc(String balanceTypeShortDesc) {
        this.balanceTypeShortDesc = balanceTypeShortDesc;
    }

    public int getAcctTypeId() {
        return acctTypeId;
    }

    public void setAcctTypeId(int acctTypeId) {
        this.acctTypeId = acctTypeId;
    }

    public String getAcctTypeDesc() {
        return acctTypeDesc;
    }

    public void setAcctTypeDesc(String acctTypeDesc) {
        this.acctTypeDesc = acctTypeDesc;
    }

    public int getAcctCatgId() {
        return acctCatgId;
    }

    public void setAcctCatgId(int acctCatgId) {
        this.acctCatgId = acctCatgId;
    }

    public String getAcctCatgDesc() {
        return acctCatgDesc;
    }

    public void setAcctCatgDesc(String acctCatgDesc) {
        this.acctCatgDesc = acctCatgDesc;
    }

}

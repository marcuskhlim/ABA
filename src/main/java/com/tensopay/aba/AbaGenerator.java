package com.tensopay.aba;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class AbaGenerator {
    private final String DESCRIPTIVE_RECORD = "0";
    private final String FILE_TOTAL_RECORD = "7";
    private final String DETAIL_RECORD = "1";
    private String abaFileContent = "";
    private int totalTransactions = 0;
    private float totalCreditAmount = 0;
    private float totalDebitAmount = 0;
    private String descriptiveString = "";
    private String detailString = "";
    private String fileTotalString = "";
    private ArrayList<String> descriptiveRecord = new ArrayList<String>();

    public void addFileDetails(Map<String,String> record) {

    }

    public void addTransaction(Map<String,String> transaction) {

    }

    public String addFileTotalRecord() {
        StringBuilder sb = new StringBuilder();
        sb.append(FILE_TOTAL_RECORD);
        sb.append("999-999");
        sb.append(addBlankSpaces(12));
        sb.append(leftPadString(String.valueOf(dollarsToCents(getNetTotal())),10,"0"));
        sb.append(leftPadString(String.valueOf(dollarsToCents(this.totalCreditAmount)),10,"0"));
        sb.append(leftPadString(String.valueOf(dollarsToCents(this.totalDebitAmount)),10,"0"));
        sb.append(addBlankSpaces(24));
        sb.append(leftPadString(String.valueOf(dollarsToCents(this.totalTransactions)),10,"0"));
        sb.append(addBlankSpaces(40));
        return sb.toString();

    }

    private String addBlankSpaces(int number) {
        String str = " ";
        String repeated = StringUtils.repeat(str, number);
        return repeated;
    }

    public String leftPadString(String value,int length,String padStr) {
        return StringUtils.leftPad(value, length, padStr);
    }

    public String rightPadString(String value,int length,String padStr) {
        return StringUtils.rightPad(value, length, padStr);
    }

    private int dollarsToCents(float amount) {
        return (int)(amount * 100);
    }

    private float getNetTotal() {
        return Math.abs(this.totalCreditAmount - this.totalDebitAmount);
    }

}
package com.tensopay.aba.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {
    public static Map<String, String> transactionCodes = new HashMap<String, String>() {
        {
            put("externally_initiated_debit", "13");
            put("externally_initiated_credit", "50");
            put("australian_government_security_interest", "51");
            put("family_allowance", "52");
            put("pay", "53");
            put("pension", "54");
            put("allotment", "55");
            put("dividend", "56");
            put("debenture", "57");
            put("note_interest", "57");
        }
    };

    public static Map<String, String> rules = new HashMap<String, String>() {
        {
            put("bsb", "/^\\d]{3}-[\\d]{3}$/");
            put("account_number", "/^[\\d]{0,9}$/");
            put("bank_name", "/^[A-Z]{3}$/");
            put("user_name", "/^[A-Za-z\\s+]{0,26}$/");
            put("account_name", "/^[A-Za-z0-9^_[\\]',?;:=#\\/.*()&%!$ @+-]{0,32}$/");
            put("user_number", "/^[\\d]{0,6}$/");
            put("description", "/^[A-Za-z\\s]{0,12}$/");
            put("indicator", "/^N|T|W|X|Y| /");
            put("reference", "/^[A-Za-z0-9^_[\\]',?;:=#\\/.*()&%!$ @+-]{0,18}$/");
            put("remitter", "/^[A-Za-z\\s+]{0,16}$/");
        }
    };

    public static Map<String, String> messages = new HashMap<String, String>() {
        {
            put("bsb", "BSB format is incorrect. The valid format is XXX-XXX");
            put("account_number", "Account number must be up to 9 digits");
            put("bank_name", "Bank name must be 3 characters long and Capitalised");
            put("user_name", "User or preferred name must be letters only and up to 26 characters long");
            // Title of account to be credited/debited
            put("account_name", "Account name must be BECS characters and up to 32 characters long");
            // User Identification Number which is allocated by APCA
            put("user_number",
                    "User number which is allocated by APCA must be up to 6 digits long. The Commonwealth bank default is 301500");
            put("description", "Description must be up to 12 characters long and letters only");
            put("indicator", "The Indicator is invalid. Must be one of N, W, X, Y or otherwise blank filled.");
            put("reference",
                    "The reference must be BECS characters and up to 18 characters long and . For example: Payroll number");
            put("remitter", "The remitter must be letters only and up to 16 characters long.");
        }
    };

    public static boolean validate(Map<String, String> record, List<String> matchRules, String recordType) {
        boolean allFieldsExist = verifyRecord(record, matchRules, recordType);
        if (!allFieldsExist) {
            return false;
        } else {
            for (String rule : matchRules) {
                String field = record.get(rule);
                String pattern = Validator.rules.get(rule);
                if (!field.matches(pattern)) {
                    System.out.println(field + " is invalid");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean validateProcessDate(String dateVal) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("DDMMYY");
            date = sdf.parse(dateVal);
            if (!dateVal.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean verifyRecord(Map<String, String> record, List<String> matchRules, String recordType) {
        List<String> l = new ArrayList<String>(record.keySet());
        List<String> matchRulesClone = new ArrayList<String>(matchRules);
        matchRulesClone.removeAll(l);
        if (matchRulesClone.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validateTransactionCode(String code) {
        if (transactionCodes.containsValue(code)) {
            return true;
        } else {
            return false;
        }
    }

}
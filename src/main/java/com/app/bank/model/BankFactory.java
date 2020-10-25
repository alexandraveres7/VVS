package com.app.bank.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankFactory {
    private static int verifyName(String name){
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher match = pattern.matcher(name);
        boolean val = match.find();
        if (!name.equals("") && !val && name.length() <=40)
            return 0;
        else
            return 1;
    }

    public static Bank createBank(String type, String name) {
        if (BankFactory.verifyName(name) == 0)
            if (type.equals("International"))
                return new InternationalBank(name, type);
        return null;
    }
}

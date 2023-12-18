package com.alibou.alibou.Core.Utils;

public class UsernameGenerator {
    public static String generateUsername(String name, String surname, String phone) {
        String concatenatedNameSurname = name + surname;
        String lastFourDigits = phone.substring(phone.length() - 4);
        return concatenatedNameSurname + lastFourDigits;
    }
}

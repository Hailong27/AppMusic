package com.example.appnghenhac.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateForm {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    public static boolean validatePhoneNumber(String phoneNumber) {
        // Sử dụng regex để kiểm tra chuỗi nhập vào có đúng định dạng số điện thoại không
        String regex = "^(0|\\+84)\\d{9,10}$";
        // Biên dịch regex thành Pattern
        Pattern pattern = Pattern.compile(regex);
        // Kiểm tra chuỗi nhập vào có khớp với regex không
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public static boolean validateConfirmPassword(String pw,String cfpw){
        return pw.equals(cfpw);
    }
}

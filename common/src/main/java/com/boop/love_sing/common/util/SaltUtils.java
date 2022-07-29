package com.boop.love_sing.common.util;

public class SaltUtils {
    public static String getSalt() {
       char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+".toCharArray();
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < 8; i++) {
           char c = chars[(int) Math.floor(Math.random() * chars.length)];
           sb.append(c);
       }
         return sb.toString();
    }
}

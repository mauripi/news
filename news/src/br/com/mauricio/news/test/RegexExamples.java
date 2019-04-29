package br.com.mauricio.news.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExamples {
    public static void main(String[]args) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("string 1234 more5 67 string89 0");
        while(m.find()) {
            System.out.println(m.group());
        }
    }
}
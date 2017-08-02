package com.example.elmira.sci;


import java.util.Calendar;

public class HeightConverter {

    private int tall = 174;
    private int average = 165;
    private int age;
    private String result;

    public String ConvertHeight(int height, String gender) {

        switch (gender){

            case "مرد":
                if (height < average) {
                    result = "کوتاه قد";
                } else if (height > tall) {
                    result = "بلند قد";
                } else {
                    result = "معمولی";
                }
                break;

            case "زن":
                if (height < average-10) {
                    result = "کوتاه قد";
                } else if (height > tall-10) {
                    result = "بلند قد";
                } else {
                    result = "معمولی";
                }
                break;
        }


        return result;
    }

    public int ConvertAge(int year, int month) {

        int cur_year = Calendar.getInstance().get(Calendar.YEAR) - 621;
        int cur_month = Calendar.getInstance().get(Calendar.MONTH)-2;
        if(cur_month<month){
            age = cur_year - year-1;
        } else {
            age = cur_year - year;
        }

        return age;
    }


}

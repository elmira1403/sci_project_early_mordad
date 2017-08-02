package com.example.elmira.sci;


public class UserInfo {

    private String phone_number;
    public String password;
    public String user_exist;
    public String email;
    public String birthday;
    public String sex;
    public String sci_type;
    public String experience_level;
    public String upper_strength;
    public String height;
    public String fitness;
    public String h_real;
    public String w_real;

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_exist(String user_exist) {
        this.user_exist = user_exist;
    }

    public void setEmail(String test) {
        this.email = test;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSci_type(String sci_type) {
        this.sci_type = sci_type;
    }

    public void setExperience_level(String experience_level) {
        this.experience_level = experience_level;
    }

    public void setUpper_strength(String upper_strength) {
        this.upper_strength = upper_strength;
    }

    public void setH_real(String h_real) {
        this.h_real = h_real;
    }

    public void setW_real(String w_real) {
        this.w_real = w_real;
    }

    public String getPhone(){
        return String.valueOf(phone_number);
    }

}

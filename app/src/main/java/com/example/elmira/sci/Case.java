package com.example.elmira.sci;


public class Case {

    private String subject;
    private String video_file;
    private String voice_file;
    private String text_file;
    private String desc;

    public String getSubject() {
        return subject;
    }

    public String getVideo_file() {
        return video_file;
    }


    public String getVoice_file() {
        return voice_file;
    }


    public String getText_file() {
        return text_file;
    }


    public String getDesc() {
        return desc;
    }


    public Case(String sub, String video, String voice, String text, String description) {

        subject = sub;
        video_file = video;
        voice_file = voice;
        text_file = text;
        desc = description;

    }
}
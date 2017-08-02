package com.example.elmira.sci.database;


public class RecCaseDbSchema {

    public static final class SolutionTable {
        public static final String NAME = "Solution";

        public static final class Cols {
            public static final String SUBJECT = "subject";
            public static final String VIDEO = "video_file";
            public static final String VOICE = "voice_file";
            public static final String TEXT = "text_file";
            public static final String DESC = "desc";
        }
    }

    public static final class FavoritesTable {
        public static final String NAME = "Favorites";

        public static final class Cols {
            public static final String F_SUBJECT = "subject";
            public static final String F_VIDEO = "video_file";
            public static final String F_VOICE = "voice_file";
            public static final String F_TEXT = "text_file";
            public static final String F_DESC = "desc";
        }
    }
}



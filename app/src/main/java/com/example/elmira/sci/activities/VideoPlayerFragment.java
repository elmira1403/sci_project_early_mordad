package com.example.elmira.sci.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.elmira.sci.R;

public class VideoPlayerFragment extends Fragment {
    VideoView videoView;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_video_player, container, false);

        String vPath = "/sci/videos/" .concat(getArguments().getString("video_link"));

        videoView = (VideoView) v.findViewById(R.id.videoView);
        videoView.setVideoPath(Environment.getExternalStorageDirectory().toString()+vPath);
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.requestFocus();
        videoView.start();
        setRetainInstance(true);

        return v;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        outState.putBundle("video", outState);
//        super.onSaveInstanceState(outState);
//    }

}

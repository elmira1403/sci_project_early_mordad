package com.example.elmira.sci.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.elmira.sci.R;

import java.io.IOException;

public class VoicePlayerFragment extends Fragment {
    static MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_voice_player, container, false);

        String url = getArguments().getString("voice_link");

        if (!url.isEmpty()) {
            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(url));

            final ImageButton playButton = (ImageButton) v.findViewById(R.id.playButton);
            ImageButton stopButton = (ImageButton) v.findViewById(R.id.stopButton);


            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playButton.setImageResource(R.drawable.play);
                    } else {
                        mediaPlayer.start();
                        playButton.setImageResource(R.drawable.pause);
                    }
                }
            });

            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.stop();
                    playButton.setImageResource(R.drawable.play);
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return v;
    }

}


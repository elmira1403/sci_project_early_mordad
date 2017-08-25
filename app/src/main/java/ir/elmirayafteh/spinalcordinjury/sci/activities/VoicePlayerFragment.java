package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.IOException;

import ir.elmirayafteh.spinalcordinjury.sci.R;

public class VoicePlayerFragment extends Fragment {
    static MediaPlayer mediaPlayer;
    ImageButton playButton;
    ImageButton stopButton;
    String vPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_voice_player, container, false);

        vPath = Environment.getExternalStorageDirectory().toString() + "/sci/voices/"
                .concat(getArguments().getString("voice_link"));

        if (!getArguments().getString("voice_link").equals("")) {
            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(vPath));

            playButton = (ImageButton) v.findViewById(R.id.playButton);
            stopButton = (ImageButton) v.findViewById(R.id.stopButton);


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

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playButton.setImageResource(R.drawable.play);
                }
            });
        }

        return v;
    }

}


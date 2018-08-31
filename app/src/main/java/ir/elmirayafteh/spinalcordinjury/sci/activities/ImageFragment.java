package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;

import ir.elmirayafteh.spinalcordinjury.sci.R;

public class ImageFragment extends Fragment {
    ImageSwitcher imageFragmentView;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_image, container, false);

        String vPath = "/sci/images/" .concat(getArguments().getString("image_link"));

        imageFragmentView = (ImageSwitcher) v.findViewById(R.id.imageFragmentView);

        return v;
    }

}

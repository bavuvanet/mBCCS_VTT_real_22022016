package com.viettel.bss.viettelpos.v4.video;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.utils.Log;

/**
 * Created by thinhhq1 on 6/30/2017.
 */

public class FragmentPlayvideo extends Fragment {

    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.video_test_layout, container, false);

        videoView = (VideoView) mView.findViewById(R.id.video_view);

        String  videoType = getArguments().getString("videoType");
        String url = "";

        if("video1".equals(videoType)){
            url = "http://10.30.147.66:8686/video/video1.mp4";
        }
        if("video2".equals(videoType)){
            url = "http://10.30.147.66:8686/video/video2.mp4";
        }
        if("video3".equals(videoType)){
            url = "http://10.30.147.66:8686/video/video3.mp4";
        }
        Log.d("URL ===================>" + url);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
//        http://10.58.71.177:8988/Apk/videotest.mp4
        videoView.setVideoURI(Uri.parse(url));
//        videoView.setVideoURI(Uri.parse("http://10.58.71.177:8988/Apk/videotest.mp4"));
        videoView.start();
        return mView;
    }


}

package com.example.youtubeplayerappbykhanhdo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayActivity extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer mYouTubePlayer;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private int mCurrentPosition = 0;
    private int mStatePosition = 0;
    private String videoId;
    private String mStateVideoId;
    public static final String PLAYBACK_TIME = "playback_time";
    public static final String VIDEO_ID = "video_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        videoId = intent.getStringExtra(VIDEO_ID);

        if (savedInstanceState != null) {
            mStateVideoId = savedInstanceState.getString(VIDEO_ID);
            if (mStateVideoId.equals(videoId)) {
                mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
            }
            else {
                mCurrentPosition = 0;
            }
        }

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayerView);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId, mCurrentPosition);
                mYouTubePlayer = youTubePlayer;
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(PlayActivity.this, "Failed to load video!", Toast.LENGTH_SHORT).show();
            }
        };

        initializePlayer();
    }

    private void initializePlayer() {
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(), mOnInitializedListener);
    }

    private void releasePlayer() {
        mYouTubePlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mStatePosition = mYouTubePlayer.getCurrentTimeMillis();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mYouTubePlayer.pause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(PLAYBACK_TIME, mStatePosition);
        bundle.putString(VIDEO_ID, videoId);
    }
}

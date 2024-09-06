package com.example.mylogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.ui.PlayerView;

import java.util.HashMap;
import java.util.Map;

@UnstableApi
public class videoplayer extends AppCompatActivity {
    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_CURRENT_WINDOW = "current_window";

    PlayerView playerView;
    ExoPlayer exoPlayer;
    Context context = this;
    Map<String, String> defaultRequestProperties = new HashMap<>();
    String userAgent = "";
    private DefaultTrackSelector trackSelector;
    ProgressBar progressBar;

    private long playbackPosition;
    private boolean playWhenReady = true;
    private int currentWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        setContentView(R.layout.videoplayer);
        playerView = findViewById(R.id.PlayerView);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("VIDEO_ID");

        DataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent(userAgent)
                .setKeepPostFor302Redirects(true)
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                .setDefaultRequestProperties(defaultRequestProperties);
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context, httpDataSourceFactory);
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build();

        MediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem);

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(KEY_CURRENT_WINDOW);
        }

        initializePlayer(progressiveMediaSource);
    }

    private LoadControl buildLoadControl() {
        return new DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                        DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                        DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                        DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                        DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS)
                .build();
    }

    @OptIn(markerClass = UnstableApi.class)
    private void initializePlayer(MediaSource progressiveMediaSource) {
        ExoTrackSelection.Factory videoSelector = new AdaptiveTrackSelection.Factory();
        trackSelector = new DefaultTrackSelector(this, videoSelector);
        exoPlayer = new ExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .setLoadControl(buildLoadControl())
                .setSeekForwardIncrementMs(10000)
                .setSeekBackIncrementMs(10000)
                .build();
        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (state == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onIsLoadingChanged(boolean isLoading) {
                // No changes needed here
            }
        });

        exoPlayer.setMediaSource(progressiveMediaSource);
        exoPlayer.prepare();
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.setPlayWhenReady(playWhenReady);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PLAYBACK_POSITION, exoPlayer.getCurrentPosition());
        outState.putBoolean(KEY_PLAY_WHEN_READY, exoPlayer.getPlayWhenReady());
        outState.putInt(KEY_CURRENT_WINDOW, exoPlayer.getCurrentWindowIndex());
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        exoPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}

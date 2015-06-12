package byteshaft.com.shoutcast;

import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mPlaybackButton;
    private FFmpegMediaPlayer mMediaPlayer;
    private boolean mPrepared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPlaybackButton = (Button) findViewById(R.id.button_toggle_playback);
        mPlaybackButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = new FFmpegMediaPlayer();
        mPlaybackButton.setText("Play");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mPrepared = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_toggle_playback:
                System.out.println(StreamService.isServiceRunning);
                if (StreamService.isServiceRunning) {
                    stopService(new Intent(getApplicationContext(), StreamService.class));
                    mPlaybackButton.setText("Play");

                } else {
                    startService(new Intent(getApplicationContext(), StreamService.class));
                    mPlaybackButton.setText("Pause");
                }
//                if (mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.pause();
//                    mPlaybackButton.setText("Play");
//                } else if (mPrepared){
//                    mMediaPlayer.start();
//                    mPlaybackButton.setText("Pause");
//                } else {
//                    prepareAndStart();
//                    mPlaybackButton.setText("Pause");
//                }
        }
    }

    private void prepareAndStart() {
        String url = getString(R.string.shoutcast_url);
        try {
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

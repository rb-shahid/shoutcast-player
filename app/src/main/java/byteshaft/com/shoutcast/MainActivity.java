package byteshaft.com.shoutcast;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;

public class MainActivity extends ActionBarActivity implements
        FFmpegMediaPlayer.OnPreparedListener, View.OnClickListener,
        FFmpegMediaPlayer.OnErrorListener {

    private Button mPlaybackButton;
    private FFmpegMediaPlayer mMediaPlayer;
    private boolean mPrepared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlaybackButton = (Button) findViewById(R.id.button_toggle_playback);
        mPlaybackButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = new FFmpegMediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
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
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPlaybackButton.setText("Play");
                } else if (mPrepared){
                    mMediaPlayer.start();
                    mPlaybackButton.setText("Pause");
                } else {
                    prepareAndStart();
                    mPlaybackButton.setText("Pause");
                }
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

    @Override
    public void onPrepared(FFmpegMediaPlayer fFmpegMediaPlayer) {
        mPrepared = true;
        fFmpegMediaPlayer.start();
    }

    @Override
    public boolean onError(FFmpegMediaPlayer fFmpegMediaPlayer, int i, int i1) {
        return false;
    }
}

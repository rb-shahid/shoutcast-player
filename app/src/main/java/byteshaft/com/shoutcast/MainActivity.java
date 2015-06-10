package byteshaft.com.shoutcast;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;


public class MainActivity extends ActionBarActivity implements FFmpegMediaPlayer.OnPreparedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        start();
    }

    private void start() {
        FFmpegMediaPlayer mp = new FFmpegMediaPlayer();
        mp.setOnPreparedListener(this);
        String url = "rtmp://levelagency.com/shoutcast/caracol.stream";
        try {
            mp.setDataSource(url);
            mp.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(FFmpegMediaPlayer fFmpegMediaPlayer) {
        fFmpegMediaPlayer.start();
    }
}

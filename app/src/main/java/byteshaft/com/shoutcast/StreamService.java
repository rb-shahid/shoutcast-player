package byteshaft.com.shoutcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;


public class StreamService extends Service implements FFmpegMediaPlayer.OnPreparedListener {

    static boolean isServiceRunning = false;
    private FFmpegMediaPlayer mMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start();
        isServiceRunning = true;
        return START_NOT_STICKY;
    }

    private void start() {
        mMediaPlayer = new FFmpegMediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        String url = getString(R.string.shoutcast_url);
        try {
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        isServiceRunning = false;
        Log.i("Stram Service", "on destroy");
    }

    @Override
    public void onPrepared(FFmpegMediaPlayer fFmpegMediaPlayer) {
        mMediaPlayer.start();
    }
}

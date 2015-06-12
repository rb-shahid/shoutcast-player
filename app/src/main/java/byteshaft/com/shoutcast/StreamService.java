package byteshaft.com.shoutcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;


public class StreamService extends Service implements FFmpegMediaPlayer.OnPreparedListener {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start();
        return START_NOT_STICKY;
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

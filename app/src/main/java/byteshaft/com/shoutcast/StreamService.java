package byteshaft.com.shoutcast;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.IOException;

import wseemann.media.FFmpegMediaPlayer;

public class StreamService extends Service implements FFmpegMediaPlayer.OnPreparedListener {

    final int ID = 404;
    FFmpegMediaPlayer mMediaPlayer;
    private static StreamService sService;

    static StreamService getInstance() {
        return sService;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sService = this;
        start();
        Notification mNotification = new Notification(getApplicationContext());
        CallStateListener CallStateListener = new CallStateListener();
        TelephonyManager telephonyManager = mNotification.getTelephonyManager();
        telephonyManager.listen(CallStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        OutGoingCallListener OutGoingCallListener = new OutGoingCallListener();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        mNotification.registerReceiver(OutGoingCallListener, intentFilter);
        android.app.Notification notification = mNotification.getNotification();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(ID, notification);
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
        mMediaPlayer.release();
        sService = null;
    }

    @Override
    public void onPrepared(FFmpegMediaPlayer fFmpegMediaPlayer) {
        mMediaPlayer.start();
    }
}

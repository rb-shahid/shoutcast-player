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
    private Notification mNotification;
    private boolean mIsPrepared;
    private boolean mPreparing;

    static StreamService getInstance() {
        return sService;
    }

    static boolean isRunning() {
        return sService != null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sService = this;
        mMediaPlayer = new FFmpegMediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mNotification = new Notification(getApplicationContext());
        CallStateListener CallStateListener = new CallStateListener();
        TelephonyManager telephonyManager = mNotification.getTelephonyManager();
        telephonyManager.listen(CallStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        OutGoingCallListener OutGoingCallListener = new OutGoingCallListener();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        mNotification.registerReceiver(OutGoingCallListener, intentFilter);
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Notification.removeNotification();
    }

    void startStream() {
        if (mIsPrepared) {
            mMediaPlayer.start();
        } else if (!mPreparing){
            String url = getString(R.string.shoutcast_url);
            try {
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPreparing = true;
        }
    }

    void pauseStream() {
        mMediaPlayer.pause();
    }

    void stopStream() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopStream();
        mNotification.removeNotification();
        sService = null;
    }

    @Override
    public void onPrepared(FFmpegMediaPlayer fFmpegMediaPlayer) {
        mIsPrepared = true;
        mMediaPlayer.start();
        android.app.Notification notification = mNotification.getNotification();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(ID, notification);
    }
}

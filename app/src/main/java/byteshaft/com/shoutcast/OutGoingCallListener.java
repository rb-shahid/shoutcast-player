package byteshaft.com.shoutcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutGoingCallListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (StreamService.getInstance().mMediaPlayer.isPlaying()) {
            StreamService.getInstance().mMediaPlayer.pause();
        }
    }
}
package byteshaft.com.shoutcast;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallStateListener extends PhoneStateListener {

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                if (StreamService.getInstance().mMediaPlayer.isPlaying()) {
                    StreamService.getInstance().mMediaPlayer.pause();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                StreamService.getInstance().mMediaPlayer.start();
        }
    }
}

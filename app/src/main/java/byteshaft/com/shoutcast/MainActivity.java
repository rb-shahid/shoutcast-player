package byteshaft.com.shoutcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mPlaybackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPlaybackButton = (Button) findViewById(R.id.button_toggle_playback);
        mPlaybackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_toggle_playback:
                if (StreamService.isServiceRunning) {
                    stopService(new Intent(getApplicationContext(), StreamService.class));
                    mPlaybackButton.setText("Play");
                } else if (!StreamService.isServiceRunning){
                    startService(new Intent(getApplicationContext(), StreamService.class));
                    mPlaybackButton.setText("Pause");
                }
        }
    }
}

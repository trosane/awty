package edu.washington.trosane.arewethereyet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Runnable;
import android.widget.Toast;
import android.telephony.SmsManager;

public class TimeService extends Service {
    public static final int INTERVAL = MainActivity.getTime() * 60000;

    private Handler mHandler = new Handler();

    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }

        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, INTERVAL);
    }

    public void onDestroy(){
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        mTimer.cancel();
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(MainActivity.getPhoneNo(), null, MainActivity.getSMS(), null, null);
                }
            });
        }
    }
}
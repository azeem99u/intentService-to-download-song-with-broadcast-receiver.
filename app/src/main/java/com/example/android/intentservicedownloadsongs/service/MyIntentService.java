package com.example.android.intentservicedownloadsongs.service;

import static com.example.android.intentservicedownloadsongs.MainActivity.BC_MESSAGE_KEY;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.android.intentservicedownloadsongs.MainActivity;

public class MyIntentService extends IntentService {

    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    public static final String MESSAGE_KEY2 = "MESSAGE_KEY2";
    private static final String TAG = "azeem";
    public static final String BC_ACTION = "BC_ACTION";

    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sendMessageToUI("destroy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String song = intent.getStringExtra(MESSAGE_KEY);
            String s = downloadSong(song);
            sendMessageToUI(s);
        }
    }

    private void sendMessageToUI(String s) {
        Intent intent = new Intent(BC_ACTION);
        intent.putExtra(BC_MESSAGE_KEY,s);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private String downloadSong(String song) {
        Log.d(TAG, "song started: "+song);
        SystemClock.sleep(3000);
        Log.d(TAG, "song downloaded: "+song);
        return song;
    };

}
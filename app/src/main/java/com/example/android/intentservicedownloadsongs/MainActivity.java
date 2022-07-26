package com.example.android.intentservicedownloadsongs;

import static com.example.android.intentservicedownloadsongs.service.MyIntentService.BC_ACTION;
import static com.example.android.intentservicedownloadsongs.service.MyIntentService.MESSAGE_KEY;
import static com.example.android.intentservicedownloadsongs.service.MyIntentService.MESSAGE_KEY2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.android.intentservicedownloadsongs.service.MyIntentService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String message;

    public static final String BC_MESSAGE_KEY = "BC_MESSAGE_KEY";
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String song = intent.getStringExtra(BC_MESSAGE_KEY);
            if (!(song == "destroy")){
                show(song);
            }else if (song == "destroy"){
                displayProgressBar(false);
                show("Service closed");
            }

        }
    };

    private TextView textView;
    private ProgressBar progressBar;
    private ScrollView mScroll;
    private final String[] songs = {
            "mere bina tu","main tera hero","sab tera"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        mScroll = findViewById(R.id.scrollLog);
        findViewById(R.id.start).setOnClickListener(view -> {
            show("Intent Service started...");
            displayProgressBar(true);
            for (String song:songs) {
                startService(new Intent(this, MyIntentService.class).putExtra(MESSAGE_KEY,song));
            }
        });
        findViewById(R.id.stop).setOnClickListener(view -> {
            textView.setText("Start Intent Service \n");
            displayProgressBar(false);
            stopService(new Intent(this, MyIntentService.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,new IntentFilter(BC_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);

    }

    public void displayProgressBar(boolean display) {
        if (display) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void show(String message) {
        textView.append(message + "\n");
        scrollTextToEnd();
    }
    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

}
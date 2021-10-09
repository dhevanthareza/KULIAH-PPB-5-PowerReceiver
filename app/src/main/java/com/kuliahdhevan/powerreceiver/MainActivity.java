package com.kuliahdhevan.powerreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private CustomReceiver mReceiver = new CustomReceiver();
    private static final String ACTION_CUSTOM_BROADCAST = "ACTION_CUSTOM_BROADCAST";
    ToggleButton toggleReeiver;
    IntentFilter filter = new IntentFilter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        this.registerReceiver(mReceiver, filter);
        toggleReeiver = findViewById(R.id.toggleButton);
        toggleReeiver.setChecked(true);
        toggleReeiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleToggleReceiverChange(isChecked);
            }
        });
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver,
                        new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }

    public void handleToggleReceiverChange(boolean isChecked) {
        if(isChecked) {
            this.registerReceiver(mReceiver, filter);
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(mReceiver,
                            new IntentFilter(ACTION_CUSTOM_BROADCAST));
        } else {
            this.unregisterReceiver(mReceiver);
            LocalBroadcastManager.getInstance(this)
                    .unregisterReceiver(mReceiver);
        }
    }
}
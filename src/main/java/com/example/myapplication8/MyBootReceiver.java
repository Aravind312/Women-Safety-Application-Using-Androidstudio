package com.example.myapplication8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service  = new Intent(context,MyService.class);
        context.startService(service);
    }
}
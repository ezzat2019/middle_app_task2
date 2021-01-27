package com.talabto.middleappbyezzat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.talabto.middleappbyezzat.services.MyForeService;

public class MainActivity extends AppCompatActivity {

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isMyServiceRunning(MyForeService.class))
        {  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(getApplicationContext(), MyForeService.class));
        } else {
            startService(new Intent(getApplicationContext(), MyForeService.class));
        }

        }

        if (getIntent().getAction()!=null)
        {
            if (getIntent().getAction().equals("show_dialog"))
            {
                String user =getIntent().getStringExtra("user");
                sendBroadcast(new Intent("com.talabto.middleappbyezzat.send").putExtra("user",user));
                showRecivedDialog(user);
            }
        }




    }


    private void showRecivedDialog(String user_data) {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("messgae from emitter app")
                .setMessage(user_data)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).create();
        alertDialog.show();
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void cancelServices(View view) {
        stopService(new Intent(getApplicationContext(), MyForeService.class));
    }
}
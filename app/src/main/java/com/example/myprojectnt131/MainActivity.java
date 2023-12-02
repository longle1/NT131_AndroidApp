package com.example.myprojectnt131;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;import androidx.core.content.ContextCompat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiService.ApiInterface apiInterface;
    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiService.getInstance().getApiInterface();
        btn = findViewById(R.id.button);
        StatusDoor statusDoorRequest = new StatusDoor();
        Intent i = getIntent();
        String username = i.getStringExtra("username");
        statusDoorRequest.setUsername(username);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusDoorRequest.setCurrentStatus(true);
                // Call the update status door API
                Call<Result> updateStatusDoorCall = apiInterface.updateStatusDoor(statusDoorRequest);
                updateStatusDoorCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result res = response.body();
                            if (res != null) {
                                showNotification();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Update is failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        // Handle network or unexpected error
                    }
                });
            }
        });
    }
    public void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Notification")
                    .setContentText("The door was opened")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
        Notification notification;
        notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID,notification);
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CharSequence channelName = "My Channel";
            String channelDiscription = "My Channel Discreption";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channelName,importance);
            channel.setDescription(channelDiscription);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
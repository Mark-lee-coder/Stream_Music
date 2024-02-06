package com.example.streammusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener {
    ImageView ivPlayPause;
    String audioLink = "https://dl.dropbox.com/s/5ey5xwb7a5ylqps/game_of_thrones.mp3?dl=0";
    boolean musicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPlayPause = findViewById(R.id.ivPlayPause);
        ivPlayPause.setBackgroundResource(R.drawable.play);

        ApplicationClass.context = (Context) MainActivity.this;

        ivPlayPause.setOnClickListener(v -> {
            if (!musicPlaying) {
                Intent intent = new Intent(MainActivity.this, MyPlayService.class);
                intent.putExtra("AudioLink", audioLink);
                startActivity(intent);
                ivPlayPause.setBackgroundResource(R.drawable.pause);
                musicPlaying = true;

                try {
                    startService(intent);
                }

                catch (SecurityException securityException) {
                    Toast.makeText(MainActivity.this, "Error: " + securityException.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            else {
                Intent intent = new Intent(MainActivity.this, MyPlayService.class);
                intent.putExtra("AudioLink", audioLink);
                startActivity(intent);
                ivPlayPause.setBackgroundResource(R.drawable.pause);
                musicPlaying = true;

                try {
                    stopService(intent);
                }

                catch (SecurityException securityException) {
                    Toast.makeText(MainActivity.this, "Error: " + securityException.getMessage(), Toast.LENGTH_SHORT).show();
                }

                ivPlayPause.setBackgroundResource(R.drawable.play);
                musicPlaying = false;
            }
        });
    }

    @Override
    public void onMusicStopped() {
        ivPlayPause.setBackgroundResource(R.drawable.play);
        musicPlaying = false;
    }
}
package com.example.youtubeplayerappbykhanhdo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playButton = findViewById(R.id.playButton);
        EditText urlEditText = findViewById(R.id.urlEditText);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoId = null;
                String url = urlEditText.getText().toString();
                if (url.contains("youtube.com") && url.contains("v=")) {
                    videoId = url.split("v=")[1];
                    if (url.contains("&")) {
                        videoId = videoId.split("&")[0];
                    }

                    playVideo(videoId);
                }
                else if (url.contains("youtu.be") || url.contains("youtube.com")) {
                    if (url.contains("youtu.be")) videoId = url.split("be/")[1];
                    if (url.contains("youtube.com")) videoId = url.split("com/")[1];

                    if (url.contains("&")) {
                        videoId = videoId.split("&")[0];
                    }

                    playVideo(videoId);
                }
                else {
                    Toast.makeText(MainActivity.this, "Link not supported! Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void playVideo(String videoId) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.putExtra("video_id", videoId);

        startActivity(intent);
    }
}

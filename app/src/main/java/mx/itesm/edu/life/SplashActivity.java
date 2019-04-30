package mx.itesm.edu.life;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        videoView = (VideoView)findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.loop1);
        videoView.setVideoURI(video);
        new Handler().postDelayed(new Runnable() {// a thread in Android
            @Override
            public void run() {
                Intent intent = new Intent( SplashActivity.this  ,  MainActivity.class  );
                startActivity(intent);
                finish();
            }
        },6500);
        videoView.start();
    }
}

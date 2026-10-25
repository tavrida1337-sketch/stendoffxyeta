package com.xyeta.suka;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.btn_play);
        Button settingsBtn = findViewById(R.id.btn_settings);
        Button aboutBtn = findViewById(R.id.btn_about);

        playBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Play button pressed!", Toast.LENGTH_SHORT).show();
        });

        settingsBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Settings button pressed!", Toast.LENGTH_SHORT).show();
        });

        aboutBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "About button pressed!", Toast.LENGTH_SHORT).show();
        });
    }

}
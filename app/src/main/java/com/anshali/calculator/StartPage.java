package com.anshali.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}

package com.example.galleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
    public void click1(View view){
        Intent intent =new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void click2(View view){
     Intent intent =new Intent(Main2Activity.this,Main3Activity.class);
     startActivity(intent);
     finish();
    }
}

package com.ucsm.tylersai.pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {
    Button btScan;
    Button btGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        btScan = findViewById(R.id.bt_scancode);
        btGenerate = findViewById(R.id.bt_generatecode);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActivity.this, ChooseQRBarActivity.class);
                startActivity(intent);
            }
        });
    }
}

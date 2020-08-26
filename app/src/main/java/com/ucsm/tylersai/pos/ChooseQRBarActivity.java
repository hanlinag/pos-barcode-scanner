package com.ucsm.tylersai.pos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseQRBarActivity extends AppCompatActivity {

    Button btGenQR;
    Button btGenBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_qrbar);

        btGenBar = findViewById(R.id.bt_gen_bar);
        btGenQR = findViewById(R.id.bt_gen_qr);

        btGenBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseQRBarActivity.this, GenBarActivity.class);
                startActivity(intent);
            }
        });

        btGenQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseQRBarActivity.this, GenQRActivity.class);
                startActivity(intent);
            }
        });

    }
}

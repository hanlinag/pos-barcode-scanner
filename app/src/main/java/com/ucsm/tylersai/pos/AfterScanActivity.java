package com.ucsm.tylersai.pos;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AfterScanActivity extends AppCompatActivity {

    private Button btSend;
    private TextView tvOutput;
    private TextView tvInfo;

    private Socket s;


    private DataOutputStream toServer ;
    private DataInputStream fromServer;


    private String messageToSend = "";
    private static String ip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        messageToSend = Common.result;
        ip = Common.ipaddr;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_scan);

        btSend = (Button)findViewById(R.id.btSend);
        tvOutput = (TextView)findViewById(R.id.tvOutput);
        tvInfo = (TextView)findViewById(R.id.tvInfo);

        tvOutput.setText(""+messageToSend);

        if(tvOutput.getText().toString().contains("777777111777")){
            tvInfo.setText("Please enter the code number to the text field in Desktop");
            btSend.setEnabled(false);

        }else{

            tvInfo.setText("");
            btSend.setEnabled(true);
        }

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    s = new Socket(ip, 5000);
                    Log.i("Tyler","ip is : "+ip);
                    toServer = new DataOutputStream(s.getOutputStream());
                    fromServer = new DataInputStream(s.getInputStream());



                    toServer.writeUTF(messageToSend);
                    toServer.flush();
                    toServer.close();


                    //s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(AfterScanActivity.this, messageToSend+"Data sent to pc",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
}

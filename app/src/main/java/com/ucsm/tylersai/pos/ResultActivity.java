package com.ucsm.tylersai.pos;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

public class ResultActivity extends AppCompatActivity {

    Socket s;


    DataOutputStream toServer ;
    DataInputStream fromServer;


    String messageToSend = "";
    private static String ip = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);





        messageToSend = Common.result;
        ip = Common.ipaddr;

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

        Toast.makeText(this, messageToSend+"Data sent to pc",Toast.LENGTH_LONG).show();
        onBackPressed();

    }
}

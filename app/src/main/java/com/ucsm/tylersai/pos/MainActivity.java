package com.ucsm.tylersai.pos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private DataOutputStream toServer;
    private  DataInputStream fromServer;

    private String macAddress;
    private String ipAddress;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        final EditText etIP = findViewById(R.id.et_ip);
        final EditText etPass = findViewById(R.id.et_pass);
        Button btContinue = findViewById(R.id.bt_continue);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String addr = wInfo.getMacAddress();



         macAddress = addr;


        Log.i("Tyler", "mac address is : "+macAddress);
        Log.i("Tyler","ip address is : "+ipAddress);
        //Toast.makeText(this,""+addr, Toast.LENGTH_LONG).show();


        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ipAddress = etIP.getText().toString();
                pass = etPass.getText().toString();
                Common.ipaddr = ipAddress;

                if(pass.equals("ucsmposcashier")){

                    Intent intent = new Intent(MainActivity.this, QRActivity.class);
                    startActivity(intent);

                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Password doesn't match.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

               /* try {
                    Socket s = new Socket(ipAddress, 5000);
                    Log.i("Tyler","ip is : "+ ipAddress);
                    toServer = new DataOutputStream(s.getOutputStream());
                    fromServer = new DataInputStream(s.getInputStream());


                    toServer.writeUTF(macAddress);

                    String result = fromServer.readUTF();

                    if (result.equals("Yes")){

                        Common.isIntegratedDevice = true;
                        //Common.ipaddr = et_ip.getText().toString();

                        Intent intent = new Intent(MainActivity.this, QRActivity.class);
                        startActivity(intent);

                    }else {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("Your mac address doesn't match.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        Common.isIntegratedDevice = false;
                    }

                    //s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        });
    }


}

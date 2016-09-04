package com.cconci.networkclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class NetworkClientMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_client_main);

        AppGlobals.context = this;
    }

    /**
     * Button Actions
     */

    public void pressButtonConnect(android.view.View view) {

        try {
            String ipAddress = ((EditText) findViewById(R.id.editTextIpAddress)).getText().toString();

            int port = Integer.parseInt(((EditText) findViewById(R.id.editTextPort)).getText().toString());

            AppGlobals.destinationIP = ipAddress;
            AppGlobals.destinationPort = port;

            //Start the connection to the server
            new NetworkClientAsyncTask().execute("");
        } catch(Exception e) {
            System.out.println("NetworkClientMainActivity : pressButtonConnect() - fail");
        }

    }

    public void pressButtonDisconnect(android.view.View view) {

        AppGlobals.netClient.stopClient();

    }

    public void pressButtonSendText(android.view.View view) {

        //Read the text from the in data
        String message  = ((EditText) findViewById(R.id.editTextTxData)).getText().toString();

        //sends a message to the server
        if (AppGlobals.netClient != null) {
            AppGlobals.netClient.sendMessageStream(message);
        }

    }

    //Context Call Back (sort of...)
    public void NetworkMessageRecived(String message) {
        System.out.println("NetworkClientMainActivity : NetworkMessageRecived()");

        EditText output = ((EditText) findViewById(R.id.editTextRXData));

        output.append(message);

    }
}

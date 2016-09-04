package com.cconci.networkclient;

import android.os.AsyncTask;

/**
 * Created by chris on 4/09/16.
 */
public class NetworkClientAsyncTask extends AsyncTask<String,String,NetworkClient> {

    public NetworkClientAsyncTask() {


    }

    @Override
    protected NetworkClient doInBackground(String... message) {


        //setup
        AppGlobals.netClient = new NetworkClient(new NetworkClient.OnMessageReceived() {

        @Override
        public void messageReceived(String message) {
            //this method calls the onProgressUpdate

            publishProgress(message);
        }

        });

        System.out.println("NetworkClientAsyncTask:doInBackground() - action");

        //run client
        AppGlobals.netClient.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        System.out.println("NetworkClientAsyncTask:onProgressUpdate() - action");

        //there is probably a much better way to do this, but for now it should be ok :)
        ((NetworkClientMainActivity)AppGlobals.context).NetworkMessageRecived(values[0]);
    }
}

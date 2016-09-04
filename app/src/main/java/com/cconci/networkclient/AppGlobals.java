package com.cconci.networkclient;

import android.content.Context;

/**
 * Created by chris on 4/09/16.
 */
public class AppGlobals {

    public static NetworkClient netClient;

    public static DeviceCommunications deviceControl = new DeviceCommunications();

    //Not sure if this is a good idea but I use it to call up the main activity
    public static Context context;

    public static String destinationIP;
    public static int destinationPort;

}

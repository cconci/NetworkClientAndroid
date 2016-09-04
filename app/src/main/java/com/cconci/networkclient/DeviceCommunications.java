package com.cconci.networkclient;

/**
 * Created by chris on 4/09/16.
 *
 * Class is a wrapper for holding specific messages to send to a device that is acting as a server
 *
 * In this system the device(server) responds to certain messages sent via TCP
 *
 */
public class DeviceCommunications {

    DeviceCommunications(){

    }

    /**
     * Generic Packet builder - free data
     */
    public int prepareRawPacketForDevice(byte[] inData , int inLength) {

        return 0;
    }

    public int prepareStringPacketForDevice(String inData) {

        return 0;
    }

    private int sendPacket(String packetData) {

        //send it
        try {
            //AppGlobals.mTcpClient = null;
            new NetworkClientAsyncTask().execute("");

        }
        catch (Exception e)
        {
            System.out.println("DeviceCommunications:sendPacket() - failed to start network communications");
        }

        if(AppGlobals.netClient != null) {

            AppGlobals.netClient.stopClient();
            AppGlobals.netClient.sendMessageStream(packetData);
            System.out.println("DeviceCommunications:sendPacket() - TX OK");

            //


        } else {
            System.out.println("DeviceCommunications:sendPacket() - Client TX Error");


        }

        return 0;
    }

    /**
     * More Specific Messages can be added here as functions
     */
    public int sendPacketToConfigureNetwork() {

        return 0;
    }

    public int sendPacketToScanForNetworks() {

        return 0;
    }

    public int sendPacketToRequestNetworkScanResults() {

        return 0;
    }
}

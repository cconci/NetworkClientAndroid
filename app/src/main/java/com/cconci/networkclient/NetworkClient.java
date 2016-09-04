package com.cconci.networkclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by chris on 4/09/16.
 */
public class NetworkClient {

    /*
	 * Built from this Example
	 * http://myandroidsolutions.funcode.ro/2012/07/20/android-tcp-connection-tutorial/
	 * */

    private String serverMessage;

    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    private String destinationIP;
    private int destinationPort;

    private int dataMode; //RAW or String

    //Bytes
    OutputStream outRaw;
    InputStream inRaw;

    //Strings
    PrintWriter outStream;
    BufferedReader inStream;

    public void setDestinationIP(String destIP) {
        this.destinationIP = destIP;
    }

    public void setDestinationPort(int destPort) {
        this.destinationPort = destPort;
    }

    public NetworkClient(OnMessageReceived listener) {
        mMessageListener = listener;

        this.dataMode = 0; //Set to Stream
    }

    public void sendMessageStream(String message){
        if (outStream != null && !outStream.checkError()) {
            outStream.println(message);
            outStream.flush();
        }
    }

    public void sendMessageRaw(String strBuf){

        //Convert to byte buffer
        byte[] byteBuf = strBuf.getBytes();


        sendMessageRaw(byteBuf,byteBuf.length);
    }

    public void sendMessageRaw(byte[] buffer,int len) {

        if (outRaw != null) {

            try {
                outRaw.write(buffer, 0, len);
                outRaw.flush();

            }
            catch(Exception e) {

            }

        }
        else
        {

        }

    }

    public void stopClient() {

        mRun = false;
    }

    public void run() {

        mRun = true;

        try {

            //set out destination details from our globals
            this.setDestinationPort(AppGlobals.destinationPort);
            this.setDestinationIP(AppGlobals.destinationIP);

            //destination
            InetAddress serverAddr = InetAddress.getByName(this.destinationIP);

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, this.destinationPort);

            System.out.println("run() - connecting to "+this.destinationIP+":"+this.destinationPort);

            try {

                if(this.dataMode == 0) {

                    //read to send data
                    outStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    //ready to read data
                    inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                    while (mRun == true) {
                        serverMessage = inStream.readLine();

                        if (serverMessage != null && mMessageListener != null) {
                            //call the method messageReceived from MyActivity class
                            mMessageListener.messageReceived(serverMessage);

                            System.out.println("run():" + serverMessage);

                        }
                        serverMessage = null;

                    }

                }
                else {
                    //send our message
                    outRaw = socket.getOutputStream();

                    //setup to receive server response
                    inRaw = socket.getInputStream();

                    byte[] buf = new byte[1024];

                    while (mRun == true) {

                        int len = inRaw.read(buf);


                        if (len > 0 && mMessageListener != null) {
                            //call the method messageReceived from MyActivity class
                            mMessageListener.messageReceived(serverMessage);


                        }

                        serverMessage = null;

                    }
                }

            } catch (Exception e) {

                System.out.println(e.getMessage());

            } finally {
                socket.close();
                System.out.println("run() - socket close() ");
            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    //Background Async task (like a callback)
    public interface OnMessageReceived {
       public void messageReceived(String message);
    }
}

package com.example.guest.domino;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketThread extends Thread {
   private volatile boolean running;
   private volatile boolean connecting;
   private volatile boolean socket_run;

   private static SocketThread instance;



    public static String IP_SERVER=" ";
    public static int PORT=6664;

    private OnUpdateRoomListListener onUpdateRoomListListener;
    private OnStateConnectionListener onStateConnectionListener;

    OutputStreamWriter outputStream;
    private PrintWriter writer;
    private BufferedReader bufferedReader;



    public void setOnStateConnectionListener(OnStateConnectionListener onStateConnectionListener) {
        this.onStateConnectionListener = onStateConnectionListener;
    }

    private Socket socket;

    public static SocketThread getInstance(){
        synchronized (SocketThread.class){
            if (instance==null){
                instance =  new SocketThread();
            }
        }
        return  instance;
    }

    private SocketThread(){

    }
    public interface OnUpdateRoomListListener{
       void OnUpdateRoomList(Room room);
    }

    public void setOnUpdateRoomListListener(OnUpdateRoomListListener onUpdateRoomListListener) {
        this.onUpdateRoomListListener = onUpdateRoomListListener;
    }

    public interface OnStateConnectionListener{

        void onUnableConnect();

        void onServerProblems();

    }

    @Override
    public synchronized void start() {
        running=true;
        super.start();
    }

    private void Connect() {

        while(!connecting) {
            try {
                InetAddress address= InetAddress.getByName(IP_SERVER);
                socket = new Socket(address,PORT);
            } catch (IOException e) {
                  onStateConnectionListener.onUnableConnect();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message){
       if(writer!=null){
           writer.write(message);
           writer.flush();
       }
    }

    private void MessageReceived(String message) {

    }

    public void CloseSocket() {
        running = false;
        socket_run=false;
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public boolean isRunning(){
        return  running;
    }




    @Override
    public void run() {
        Connect();
        try {
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            writer=new PrintWriter(outputStream,false);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (socket_run) {
                if (writer.checkError()) {
                    socket_run = false;
                }

                String mServerMessage = bufferedReader.readLine();
             ;
                if (mServerMessage != null) {
                    MessageReceived(mServerMessage);
                }
            }
        } catch (Exception e) {
        } finally {
            if (socket != null && socket.isConnected()) {
                try {
                    socket.close();
                    socket_run=false;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }


    }
}

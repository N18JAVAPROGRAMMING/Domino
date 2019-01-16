package com.example.guest.domino;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
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



    public static String IP_SERVER="192.168.43.106";
    public static int PORT=6665;

    private OnUpdateRoomListListener onUpdateRoomListListener;
    private OnStateConnectionListener onStateConnectionListener;


    private  ExampleListener exampleListener;



    private OutputStreamWriter outputStream;
    private PrintWriter writer;
    private BufferedReader bufferedReader;
    private DataInputStream in;
    private Socket socket;





    public synchronized void setOnStateConnectionListener(OnStateConnectionListener onStateConnectionListener) {
        this.onStateConnectionListener = onStateConnectionListener;
    }



    public static SocketThread getInstance(){
        synchronized (SocketThread.class){
            if (instance==null){
                instance =  new SocketThread();
            }
        }
        return  instance;
    }

    public void setOnExampleListener(ExampleListener exampleListener){
        this.exampleListener=exampleListener;
    }

    public interface ExampleListener{
        void onExampleListener(String s);
    }

    private SocketThread(){

    }


    interface OnUpdateRoomListListener{

        void onAddNewRoom(Room room);

        void onDeleteRoom(Room room);

        void onStartRoom(Room room);

    }


    public synchronized void setOnUpdateRoomListListener(OnUpdateRoomListListener onUpdateRoomListListener) {
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
                Log.d("Socket","Подключено");
                socket_run=true;
                connecting=true;
            } catch (IOException e) {
                if(onStateConnectionListener!=null)
                  onStateConnectionListener.onUnableConnect();
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
        connecting=false;
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
            writer=new PrintWriter(outputStream,true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in= new DataInputStream(socket.getInputStream());
            while (socket_run) {
//                if (writer.checkError()) {
//                    socket_run = false;
//                }

                Log.d("socket","wait");
                writer.write("SMS");

                Log.d("socket","message was sent");


               //String m =bufferedReader.readLine();
                String mServerMessage = bufferedReader.readLine();
                Log.d("socket",mServerMessage);

                if (mServerMessage != null) {
                    Log.d("socket","getsocket1");
                    exampleListener.onExampleListener(mServerMessage);
                    //MessageReceived(mServerMessage);
                }


            }
        } catch (Exception e) {
   //e.printStackTrace();
            Log.d("socket","pizda");
        } finally {
            if (socket != null && socket.isConnected()) {
                Log.d("socket","close");
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

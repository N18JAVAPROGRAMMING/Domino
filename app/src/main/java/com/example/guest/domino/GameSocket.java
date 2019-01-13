package com.example.guest.domino;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class GameSocket extends AsyncTask<WatchData , Integer, Integer>{


    Context mCtx;
    Socket mySock;

    protected void onProgressUpdate(Integer... progress)
    { }

    protected void onPostExecute(Integer result)
    {
// Это выполнится после завершения работы потока
    }




    protected Integer doInBackground(WatchData... param)
    {
        InetAddress serverAddr;

        try {
            while(true)
            {
                serverAddr = InetAddress.getByName("192.168.0.10");
                mySock = new Socket(serverAddr, 4505);


                SocketData data = new SocketData();
                data.ctx = mCtx;
                data.sock = mySock;

// ВНИМАНИЕ! Финт ушами - еще один поток =)
// Именно он будет принимать входящие сообщения
                GetPacket pack = new GetPacket();
                AsyncTask<SocketData, Integer, Integer> running = pack.execute(data);

                String message = email;
// Посылаем email на сервер
                try {
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(mySock.getOutputStream())),true);
                    out.println(message);

                } catch(Exception e){

                }

// Следим за потоком, принимающим сообщения
                while(running.getStatus().equals(AsyncTask.Status.RUNNING))
                {

                }

// Если поток закончил принимать сообщения - это означает,
// что соединение разорвано (других причин нет).
// Это означает, что нужно закрыть сокет
// и открыть его опять в бесконечном цикле (см. while(true) выше)
                try
                {
                    mySock.close();
                }
                catch(Exception e)
                {}
            }
        } catch (Exception e) {
            return -1;
        }
    }


}

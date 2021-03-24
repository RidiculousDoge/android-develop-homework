package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;
    private boolean stopFlag=false;

    //head请求内容
    private static String content = "HEAD /xxjj/index.html HTTP/1.1\r\nHost:www.sjtu.edu.cn\r\n\r\n";

    public void disconnect() {
        stopFlag = true;
    }
    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        try{
            Socket socket=new Socket("www.sjtu.edu.cn", 80);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            double n = 1;
            byte[] data = new byte[1024 * 5];//每次读取的字节数
            int len = -1;
            while(!stopFlag&& socket.isConnected()){
                Log.d("socket","客户端发送"+content);
                os.write(content.getBytes());
                os.flush();
                int receiveLen=is.read(data);
                if(receiveLen!=-1){
                    String receive=new String(data,0,receiveLen);
                    Log.d("socket","客户端收到"+receive);

                }else{
                    Log.d("socket","客户端收到-1");
                }
                sleep(300);
            }
            Log.d("socket","客户端断开 ");
            os.flush();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
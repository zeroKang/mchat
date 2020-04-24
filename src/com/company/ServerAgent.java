package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerAgent extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private MultiServer multiServer;

    public ServerAgent(Socket socket, MultiServer mserver)throws Exception{
        this.socket = socket;
        this.multiServer = mserver;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            doJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doJob()throws Exception{
        while(true){
            String msg = dataInputStream.readUTF();
            System.out.println(msg);
            multiServer.broadcast(msg);
        }
    }

    public void sendMsg(String msg) throws Exception{
        this.dataOutputStream.writeUTF(msg);
    }
}

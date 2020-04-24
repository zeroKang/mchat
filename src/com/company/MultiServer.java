package com.company;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiServer {

    private ServerSocket serverSocket;
    private List<ServerAgent> agentList;

    MultiServer()throws Exception{

        this.serverSocket = new ServerSocket(8888);
        this.agentList = new ArrayList<>();
    }

    public void service(){
        while(true){
            try {
                Socket socket = serverSocket.accept();
                System.out.println(socket);

                ServerAgent agent = new ServerAgent(socket, this);
                synchronized (agentList) {
                    agentList.add(agent);
                    agent.start();
                }

            }catch(Exception e){

            }//end catch

        }//end while
    }

    public synchronized void broadcast(String msg) {

        for (ServerAgent agent : agentList) {
            try {
                agent.sendMsg(msg);
            } catch (Exception e) {
                agentList.remove(agent);
            }
        }

    }
}

package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class Server {
    public static void main(String args[]) throws IOException{
        ServerSocket serverSocket;
        Socket cSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        
        try{
            serverSocket = new ServerSocket(2000);
            cSocket = serverSocket.accept();
            out = new PrintWriter(cSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            sender.start();
            
            Thread receive;
            receive = new Thread(new Runnable() {
                String msg;
                long cmd;
                @Override
                public void run() {
                    try{
                        msg = in.readLine();
                        while(msg != null){  
                            System.out.println("Client: " + msg);
                            msg = in.readLine();
                            
                            if(msg.matches("SYNC")){
                                System.out.println("T");
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                System.out.println(timestamp.getTime());
                                cmd = timestamp.getTime();
                                out.println("TST::" + cmd);
                                out.flush();
                            }
                        }
                        
                        System.out.println("Client disconnected");
                        
                        out.close();
                        cSocket.close();
                        serverSocket.close();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

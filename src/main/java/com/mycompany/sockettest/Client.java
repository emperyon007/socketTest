package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    public static void main(String args[]) throws IOException{
        Socket cSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try{
            cSocket = new Socket("127.0.0.1", 2000);
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
            
            Thread receiver = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try{
                        msg = in.readLine();
                        while(msg != null){
                            System.out.println("Server: " + msg);
                            msg = in.readLine();
                            
                            if(msg.substring(0,5).equals("TST::")){
                                Date d = new Date(Long.parseLong(msg.substring(5)));
                                System.out.println("Data odierna: " + sdf.format(d));
                            }
                        }
                        
                        System.out.println("Server out of service");
                        out.close();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

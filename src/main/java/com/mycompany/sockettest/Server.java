package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String args[]) throws IOException{
        boolean flag = true;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        ServerSocket serverSocket = new ServerSocket(2000);
        Socket socket = serverSocket.accept();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        System.out.println("Client connected");
        
        out.write("Connected");
        
        String cmd;
        try
        {
            while(!(cmd = in.readLine()).equals("SYNC")) 
            {
                System.out.println("Recieved: " + cmd);

                Process p = Runtime.getRuntime().exec(cmd);
                BufferedReader pRead = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;
                while ((line = pRead.readLine()) != null) 
                {
                    System.out.println(line);
                    out.write(line + "\n");
                    out.flush();
                }
            }
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        } 
        finally 
        {
            out.write(timestamp.toString());
            socket.close();
            in.close();
            out.close();
        }
    }
}

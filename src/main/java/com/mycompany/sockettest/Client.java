package com.mycompany.sockettest;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String args[]) throws IOException{
        Socket socket = new Socket("127.0.0.1", 2000);
        
        try
    {
        BufferedReader sysRead = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String response = in.readLine();
        System.out.println("Server: " + response);

        boolean flag = true;
        while (flag)
        {
            System.out.println("Type a command... type END to close the server");
            String cmd = sysRead.readLine();
            out.write(cmd + "\n");
            out.flush();
            if (cmd.equals("SYNC"))
            {
                BufferedReader pRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                System.out.println(pRead);
                
                socket.close();
                sysRead.close();
                in.close();
                out.close();
                flag = false;
            } else
            {
                String outputline;
                while ((outputline = in.readLine()) != null)
                    System.out.println(outputline);
            }
        }
    }
    catch (IOException ex)
    {
        ex.printStackTrace();
    }
    }
}

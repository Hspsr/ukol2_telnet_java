package utb.fai;

import java.io.*;
import java.net.*;

public class TelnetClient {

    private String serverIp;
    private int port;
    private String consoleInput;

    public TelnetClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void run() {
        try {
            Socket socket = new Socket(serverIp, port);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);
            
                        
            Thread inputThread = new Thread(){
                @Override
                public void run() {   
                    try{
                        while ((consoleInput = inputReader.readLine()) != null) {
                            serverWriter.println(consoleInput);
                            if (consoleInput.equalsIgnoreCase("/QUIT")) {
                                socket.close();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }                     
                }
            };

            Thread outputThread = new Thread(){
                @Override
                public void run(){
                    try{
                        String serverResponse;
                        while((serverResponse = outputReader.readLine()) != null){
                            System.out.println(serverResponse);
                            if (serverResponse.equalsIgnoreCase("/QUIT")){
                                break;
                            }
                        } 
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            inputThread.start();
            outputThread.start();

            inputThread.join();
            outputThread.join();

            outputReader.close();
            serverWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}

package server;

import java.io.*;
import java.net.Socket;

public class ServerClientDialog implements Runnable {
    private final Socket client;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private OutputStream os = null;

    public ServerClientDialog(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {

            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            String fileName = inputStream.readUTF();
            System.out.println("Request from android is "+fileName);
            File myFile = new File(fileName);
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = client.getOutputStream();
            System.out.println("Sending " + fileName + " (" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            inputStream.close();
            os.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

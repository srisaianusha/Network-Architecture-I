import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2ServerPartC implements Runnable {

  private static Socket clientSocket = null;
  private static PrintStream pos = null;
  private static DataInputStream dis = null;

  private static BufferedReader inputLine = null;
  private static boolean clientClosed = false;
  
  public static void main(String[] args) {

    System.out.println("********************************************************");
    System.out.println("Welcome to Multi-Chat Application Client Started Running");
    System.out.println("*********************************************************");
	
    int portNumber = 2356;
    String host = "Server.NA-Project2.ch-geni-net.instageni.umkc.edu";

    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      pos = new PrintStream(clientSocket.getOutputStream());
      dis = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("UnknownHostException" + host);
    } catch (IOException e) {
      System.err.println("IOException"+ host);
    }

   
    if (clientSocket != null && pos != null && dis != null) {
      try {

        new Thread(new Client2ServerPartC()).start();
        while (!clientClosed) {
          pos.println(inputLine.readLine().trim());
        }
        pos.close();
        dis.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  @SuppressWarnings("deprecation")
  public void run() {
    String responseLine;
    try {
    	BufferedReader k
        = new BufferedReader(new InputStreamReader(dis));
    	
    	    	
      while ((responseLine = k.readLine()) != null) {
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      clientClosed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}




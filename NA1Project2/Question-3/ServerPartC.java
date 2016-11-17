import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class ServerPartC{

  private static ServerSocket serverSocket = null;
  
  private static Socket cSocket = null;

  private static final int maxCntOfClient = 10;
  private static final clientServerThread[] clientServerThreads = new clientServerThread[maxCntOfClient];

  public static void main(String args[]) {
	System.out.println("*********************************************");
    System.out.println("Multi-Chat Application Server Started Running");
    System.out.println("*********************************************");
    
    System.out.println("Waiting for users to join......");
    
    try {
      serverSocket = new ServerSocket(2356);
    } catch (IOException e) {
      System.out.println(e);
    }

    //connection socket for each client
    while (true) {
      try {
        cSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxCntOfClient; i++) {
          if (clientServerThreads[i] == null) {
            (clientServerThreads[i] = new clientServerThread(cSocket, clientServerThreads)).start();
            break;
          }
        }
        if (i == maxCntOfClient) {
          PrintStream output = new PrintStream(cSocket.getOutputStream());
          output.close();
          cSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

//clientServerThreads to accept the clients
class clientServerThread extends Thread {

  private String clientName = null;
  private DataInputStream inputStream = null;
  private PrintStream outStream = null;
  private Socket cSocket = null;
  private final clientServerThread[] clientServerThreads;
  private int maxCntOfClient;

  public clientServerThread(Socket cSocket, clientServerThread[] clientServerThreads) {
    this.cSocket = cSocket;
    this.clientServerThreads = clientServerThreads;
    maxCntOfClient = clientServerThreads.length;
  }

  @SuppressWarnings("deprecation")
  public void run() {
    int maxCntOfClient = this.maxCntOfClient;
    clientServerThread[] clientServerThreads = this.clientServerThreads;

    try {
    	inputStream = new DataInputStream(cSocket.getInputStream());
      outStream = new PrintStream(cSocket.getOutputStream());
      String name;
      while (true) {
        outStream.println("Hi User..Enter your name:");
        BufferedReader d
        = new BufferedReader(new InputStreamReader(inputStream));
        name = d.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        } 
      }
	  
      outStream.println("Hello " + name + ",enter message to start chat");
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientServerThreads[i] != null && clientServerThreads[i] == this) {
            clientName = "Whisper" + name;
            break;
          }
        }
      }
      while (true) {
    	  
    	  BufferedReader d
          = new BufferedReader(new InputStreamReader(inputStream));
    	  
        String line = d.readLine();
		System.out.println("<" + name + "> " + line);
        if (line.startsWith("/exit")) {
          break;
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientServerThreads[i] != null && clientServerThreads[i] != this
              && clientServerThreads[i].clientName != null) {
		     System.out.println( name + " exited from chat");
          }
        }
      }
      outStream.println( name + " left from Chat");
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientServerThreads[i] == this) {
            clientServerThreads[i] = null;
          }
        }
      }
      inputStream.close();
      outStream.close();
      cSocket.close();
    } catch (IOException e) {
    }
  }
}


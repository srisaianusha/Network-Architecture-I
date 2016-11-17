import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class ServerPartD{

  private static ServerSocket serverSocket = null;
  
  private static Socket cSocket = null;

  // Client count can accept maximum 10
  private static final int maxCntOfClient = 10;
  private static final clientThread[] clientThreads = new clientThread[maxCntOfClient];

  public static void main(String args[]) {
	System.out.println("*********************************************");
    System.out.println("Multi-Chat Application Server Started Running");
    System.out.println("*********************************************");
    
    int portNumber = 2356;


   // if (args.length < 1) {
//      System.out.println("Port number=" + portNumber);

 //   } else {
//      portNumber = Integer.valueOf(args[0]).intValue();
 //   }
    System.out.println("Waiting for users to join......");
    
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    //connection socket for each client
    while (true) {
      try {
        cSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxCntOfClient; i++) {
          if (clientThreads[i] == null) {
            (clientThreads[i] = new clientThread(cSocket, clientThreads)).start();
            break;
          }
        }
        if (i == maxCntOfClient) {
          PrintStream output = new PrintStream(cSocket.getOutputStream());
          output.println("Server is Busy");
          output.close();
          cSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

//clientThreads to accept the clients
class clientThread extends Thread {

  private String clientName = null;
  private DataInputStream inputStream = null;
  private PrintStream outStream = null;
  private Socket cSocket = null;
  private final clientThread[] clientThreads;
  private int maxCntOfClient;

  public clientThread(Socket cSocket, clientThread[] clientThreads) {
    this.cSocket = cSocket;
    this.clientThreads = clientThreads;
    maxCntOfClient = clientThreads.length;
  }

  @SuppressWarnings("deprecation")
  public void run() {
    int maxCntOfClient = this.maxCntOfClient;
    clientThread[] clientThreads = this.clientThreads;

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
        } else {
          outStream.println("The name should not contain '@' symbol");
        }
      }

      outStream.println("Hello " + name + ",enter message to start chat");
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientThreads[i] != null && clientThreads[i] == this) {
            clientName = "Whisper" + name;
            break;
          }
        }
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientThreads[i] != null && clientThreads[i] != this) {
            clientThreads[i].outStream.println("New User " + name
                + " joined Multi-Chat Application");
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
        if (line.startsWith("Whisper")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxCntOfClient; i++) {
                  if (clientThreads[i] != null && clientThreads[i] != this
                      && clientThreads[i].clientName != null
                      && clientThreads[i].clientName.equals(words[0])) {
                    clientThreads[i].outStream.println("<" + name + "> " + words[1]);
                    this.outStream.println(">" + name + "> " + words[1]);
                    break;
                  }
                }
              }
            }
          }
        } else {
          synchronized (this) {
            for (int i = 0; i < maxCntOfClient; i++) {
              if (clientThreads[i] != null && clientThreads[i].clientName != null) {
                clientThreads[i].outStream.println("<" + name + "> " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientThreads[i] != null && clientThreads[i] != this
              && clientThreads[i].clientName != null) {
		     System.out.println( name + " exited from chat");
          }
        }
      }
      outStream.println( name + " left from Chat");
      synchronized (this) {
        for (int i = 0; i < maxCntOfClient; i++) {
          if (clientThreads[i] == this) {
            clientThreads[i] = null;
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


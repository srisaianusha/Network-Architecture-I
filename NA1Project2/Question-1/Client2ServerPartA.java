import java.io.*; 
import java.net.*; 
import java.util.Scanner;
class Client2ServerPartA { 

	public static void main(String argv[]) throws Exception 
	{ 
		String input = ""; 
		String serverinput=""; 

		BufferedReader bin = 
				new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("Client Started Running");
		System.out.println("*****************************************");
		
			//opening socket connection
			Socket clientSocket=null; 

			while(!input.equals("exit")){
				System.out.println("Enter Message(Client): ");
				clientSocket= new Socket("Server.NA-Project2.ch-geni-net.instageni.umkc.edu", 4567);
				DataOutputStream outToServer = 
						new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader inFromServer = 
						new BufferedReader(new
								InputStreamReader(clientSocket.getInputStream())); 

				input = bin.readLine(); 

				outToServer.writeBytes(input + '\n'); 

				serverinput = inFromServer.readLine(); 

				System.out.println("Message Recieved From Server : " + serverinput); 

			}           
			clientSocket.close(); 
	} 
} 

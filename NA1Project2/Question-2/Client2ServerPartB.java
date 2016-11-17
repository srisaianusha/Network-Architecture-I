import java.io.*; 
import java.net.*; 
import java.util.Scanner;

class Client2ServerPartB { 

	public static void main(String argv[]) throws Exception 
	{ 
		String response = ""; 
		String serverResponse=""; 

		BufferedReader bin = 
				new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("Client Started Running");
		System.out.println("*****************************************");
		
			//opening socket connection
			Socket clientSocketConn=null; 

			while(!response.equals("exit")){
				System.out.println("Enter Message(Client): ");
				clientSocketConn= new Socket("Server.NA-Project2.ch-geni-net.instageni.umkc.edu", 1234);
				DataOutputStream serverOutputMsg = 
						new DataOutputStream(clientSocketConn.getOutputStream()); 
				BufferedReader binServer = 
						new BufferedReader(new
								InputStreamReader(clientSocketConn.getInputStream())); 

				response = bin.readLine(); 

				serverOutputMsg.writeBytes(response + '\n'); 
				
				serverResponse = binServer.readLine(); 

				System.out.println("Message Recieved From Server : " + serverResponse); 
			}           
			clientSocketConn.close(); 
	} 
} 


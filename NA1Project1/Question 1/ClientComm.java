import java.io.*; 
import java.net.*; 
import java.util.Scanner;

import javax.naming.InitialContext;
class ClientComm { 

	public static void main(String argv[]) throws Exception 
	{ 
		String message = ""; 
		String servermessage=""; 
		int flag=0;
		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("Client is running");
		System.out.println("*****************************************");		
		String initialmessage="";		
			Socket clientSocket=null; 
			if(flag==0){
				System.out.println("ENTER MESSAGE TO BE SENT: ");
				initialmessage = inFromUser.readLine(); 
			}
			if(initialmessage.startsWith("Hello from Client")){
				message=initialmessage;
			
			while(!servermessage.startsWith("Bye from Server" )){
				
				clientSocket= new Socket("Server.sgr43.ch-geni-net.instageni.umkc.edu", 12345);
				DataOutputStream outToServer = 
						new DataOutputStream(clientSocket.getOutputStream()); 
				BufferedReader inFromServer = 
						new BufferedReader(new
								InputStreamReader(clientSocket.getInputStream())); 
				if(flag==1){
					System.out.println("ENTER MESSAGE TO BE SENT: ");
					message = inFromUser.readLine(); 
				}
				outToServer.writeBytes(message + '\n'); 

				servermessage = inFromServer.readLine(); 

				System.out.println("MESSAGE FROM SERVER: " + servermessage); 
				flag=1;
			}           
			clientSocket.close(); 
		}
        else{
             System.out.println("Enter correct initial message");
            }
		
	} 
} 
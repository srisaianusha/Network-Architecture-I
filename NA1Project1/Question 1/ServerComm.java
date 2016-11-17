import java.io.*; 
import java.net.*; 

class ServerComm { 

	public static void main(String argv[]) throws Exception 
	{ 
		String clientSentence; 
		String returnSentence=""; 
		System.out.println("Server running");
		ServerSocket welcomeSocket = new ServerSocket(12345); 

		while(true) { 


			Socket connectionSocket = welcomeSocket.accept(); 

			BufferedReader inFromClient = 
					new BufferedReader(new
							InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream  outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream()); 

			clientSentence = inFromClient.readLine(); 
			System.out.println("RECEIVED FROM CLIENT : "+clientSentence);
			if(clientSentence.contains("Hello from Client")){
				String[] parts = clientSentence.split("-");
				returnSentence = "Hello from Server-"+parts[1]+ '\n'; 
			}
			else if(clientSentence.startsWith("Bye from Client")){
				String[] parts = clientSentence.split("-");
				returnSentence = "Bye from Server-"+parts[1]+ '\n'; 
				outToClient.writeBytes(returnSentence);
				break;
			}
			else{

				returnSentence = clientSentence+ '\n'; 
			}
			outToClient.writeBytes(returnSentence); 
		} 
	} 
} 


import java.io.*; 
import java.net.*; 

class ServerPartA { 

	public static void main(String argv[]) throws Exception 
	{ 
		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		String clientSentence; 
		String returnSentence=""; 
		System.out.println("Server Started Running");
		System.out.println("************************************");
		ServerSocket welcomeSocket = new ServerSocket(4567); 
		String sentence;

		while(true) { 
			Socket connectionSocket = welcomeSocket.accept(); 

			BufferedReader inFromClient = 
					new BufferedReader(new
							InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream  outToClient = 
					new DataOutputStream(connectionSocket.getOutputStream()); 

			clientSentence = inFromClient.readLine(); 

			System.out.println("Message Received From Client : "+clientSentence);

			if(clientSentence.equals("exit")){
				sentence="exit";
				returnSentence = sentence+ '\n'; 

				outToClient.writeBytes(returnSentence);
				connectionSocket.close(); 
				welcomeSocket.close();
				return;
			}
			else{
				System.out.println("Enter Message(Server):");
				sentence = inFromUser.readLine();
				returnSentence = sentence+ '\n'; 
				outToClient.writeBytes(returnSentence); 
			}

		} 
	} 
} 



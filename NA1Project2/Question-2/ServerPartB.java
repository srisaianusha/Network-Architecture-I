import java.io.*; 
import java.net.*; 

class ServerPartB { 


	public static void main(String argv[]) throws Exception 
	{ 
		int flag=0;
		BufferedReader inFromUser = 
				new BufferedReader(new InputStreamReader(System.in)); 
		String clientSentence=""; 
		String returnSentence=""; 
		System.out.println("Server Started Running");
		System.out.println("************************************");
		
		while(true){
			String sentence;
			ServerSocket welcomeSocket = new ServerSocket(1234);
			while(true&flag==0) { 
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


					System.out.println("Waiting for client to join");
					outToClient.writeBytes(returnSentence);
					outToClient.close();
					connectionSocket.close(); 
					welcomeSocket.close();
					flag=1;
				}
				else{
					System.out.println("Enter Message(Server):");
				    sentence = inFromUser.readLine();
				    returnSentence = sentence+ '\n'; 
				    outToClient.writeBytes(returnSentence);
					outToClient.close();
				}

			} 
			flag=0;
		} 
	}
} 



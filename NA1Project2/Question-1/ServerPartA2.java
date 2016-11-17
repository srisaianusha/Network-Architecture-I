import java.io.*; 
import java.net.*; 

class ServerPartA2 { 

	public static void main(String argv[]) throws Exception 
	{ 
		BufferedReader bin = 
				new BufferedReader(new InputStreamReader(System.in)); 
		String clientInput; 
		String response=""; 
		System.out.println("Server Started Running");
		System.out.println("************************************");
		ServerSocket openSocConn = new ServerSocket(4567); 
		String clientSentence;

		while(true) { 
			Socket socketConn = openSocConn.accept(); 

			BufferedReader binClient = 
					new BufferedReader(new
							InputStreamReader(socketConn.getInputStream()));
			DataOutputStream  clientOutMsg = 
					new DataOutputStream(socketConn.getOutputStream()); 

			clientInput = binClient.readLine(); 

			System.out.println("Message Received From Client : "+clientInput);

			if(clientInput.equals("exit")){
				clientSentence="exit";
				response = clientSentence+ '\n'; 

				clientOutMsg.writeBytes(response);
				socketConn.close(); 
				openSocConn.close();
				return;
			}
			else{
				clientOutMsg.close(); 
			}

		} 
	} 
} 



